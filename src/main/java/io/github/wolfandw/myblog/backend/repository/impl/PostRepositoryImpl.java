package io.github.wolfandw.myblog.backend.repository.impl;

import io.github.wolfandw.myblog.backend.model.Post;
import io.github.wolfandw.myblog.backend.model.PostTag;
import io.github.wolfandw.myblog.backend.repository.PostRepository;
import io.github.wolfandw.myblog.backend.repository.mapper.PostRowMapper;
import io.github.wolfandw.myblog.backend.repository.mapper.PostTagsRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация {@link PostRepository}
 */
@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PostRowMapper postRowMapper;
    private final PostTagsRowMapper postTagsRowMapper;

    /**
     * Создвние репозитория для работы с постами.
     *
     * @param jdbcTemplate      {@link JdbcTemplate}
     * @param postRowMapper     маппер результато запроса в посты
     * @param postTagsRowMapper маппер результатов запроса в тэги постов
     */
    public PostRepositoryImpl(JdbcTemplate jdbcTemplate, PostRowMapper postRowMapper,
                              PostTagsRowMapper postTagsRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.postRowMapper = postRowMapper;
        this.postTagsRowMapper = postTagsRowMapper;
    }

    @Override
    public List<Post> getPosts(List<String> searchWords, List<String> tags, int pageNumber, int pageSize) {
        List<Post> posts = executeQueryGetPosts(searchWords, tags, pageNumber, pageSize);
        Long[] postIds = posts.stream().map(Post::getId).toArray(Long[]::new);
        List<PostTag> postTags = executeQueryGetPostsTags(postIds);
        Map<Long, List<String>> postTagsMap = postTags.stream().collect(Collectors.groupingBy(PostTag::getPostId,
                Collectors.mapping(PostTag::getName, Collectors.toList())));
        posts.forEach(p -> p.setTags(postTagsMap.getOrDefault(p.getId(), List.of())));
        return posts;
    }

    @Override
    public Optional<Post> getPost(Long postId) {
        Optional<Post> post = executeQueryGetPost(postId);
        post.ifPresent(p -> updatePostTags(postId, p));
        return post;
    }

    @Override
    public int getPostsCount(List<String> searchWords, List<String> tags) {
        Optional<Integer> postsCount = executeQueryGetPostsCount(searchWords, tags);
        return postsCount.orElse(0);
    }

    @Override
    public Optional<Post> createPost(String title, String text, List<String> tags) {
        Optional<Long> newPostId = executeQueryInsertPost(title, text);
        Optional<Post> post = newPostId.flatMap(this::executeQueryGetPost);
        post.ifPresent(p -> {
            executeQueryInsertTags(tags);
            executeQueryInsertPostTags(p.getId(), tags);
            updatePostTags(p.getId(), p);
        });
        return post;
    }

    @Override
    public Optional<Post> updatePost(Long postId, String title, String text, List<String> tags) {
        executeQueryUpdatePost(postId, title, text);
        Optional<Post> post = executeQueryGetPost(postId);
        post.ifPresent(p -> {
            executeQueryInsertTags(tags);
            executeQueryDeletePostTags(p.getId());
            executeQueryInsertPostTags(p.getId(), tags);
            updatePostTags(p.getId(), p);
        });
        return post;
    }

    @Override
    public void deletePost(Long postId) {
        executeQueryDeletePostTags(postId);
        executeQueryDeletePost(postId);
    }

    @Override
    public int increasePostLikesCount(Long postId) {
        executeQueryIncreasePostLikesCount(postId);
        Optional<Post> post = executeQueryGetPost(postId);
        return post.map(Post::getLikesCount).orElse(0);
    }

    @Override
    public void increasePostCommentCount(Long postId) {
        executeQueryIncreasePostCommentsCount(postId);
    }

    @Override
    public void decreasePostCommentCount(Long postId) {
        executeQueryDecreasePostCommentsCount(postId);
    }


    private List<Post> executeQueryGetPosts(List<String> searchWords, List<String> tags, int pageNumber, int pageSize) {
        List<Object> args = new ArrayList<>();
        List<Integer> argTypes = new ArrayList<>();
        String where = buildGetPostsWhere(searchWords, tags, args, argTypes);
        String query = """
                SELECT
                    id,
                    title,
                    text,
                    likes_count,
                    comments_count,
                    image_name
                FROM post AS post_table
                """ + where + " ORDER BY post_table.created_at DESC LIMIT ? OFFSET ?";

        args.add(Math.max(pageSize, 0));
        argTypes.add(Types.INTEGER);

        args.add(Math.max((pageNumber - 1) * pageSize, 0));
        argTypes.add(Types.INTEGER);

        return jdbcTemplate.query(query, args.toArray(), RepositoryUtil.toIntArray(argTypes), postRowMapper);
    }

    private Optional<Integer> executeQueryGetPostsCount(List<String> searchWords, List<String> tags) {
        List<Object> args = new ArrayList<>();
        List<Integer> argTypes = new ArrayList<>();
        String where = buildGetPostsWhere(searchWords, tags, args, argTypes);
        String query = """
                SELECT
                    COUNT(*)
                FROM post AS post_table
                """ + where;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query,
                    args.toArray(),
                    RepositoryUtil.toIntArray(argTypes),
                    Integer.class));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private String buildGetPostsWhere(List<String> searchWords,
                                      List<String> tags,
                                      List<Object> args,
                                      List<Integer> argTypes) {
        if (searchWords.isEmpty() && tags.isEmpty()) {
            return "";
        }

        StringBuilder where = new StringBuilder(" WHERE ");
        if (!searchWords.isEmpty()) {
            String searchString = String.join(" ", searchWords);
            where.append("post_table.title LIKE ?");
            args.add("%" + searchString + "%");
            argTypes.add(Types.VARCHAR);
        }

        if (!tags.isEmpty()) {
            if (!searchWords.isEmpty()) {
                where.append(" AND ");
            }
            where.append(tags.stream().map(tag -> {
                args.add(tag);
                argTypes.add(Types.VARCHAR);
                return """
                        EXISTS (SELECT
                                    1
                                FROM post_tag AS post_tag_table
                                LEFT JOIN tag AS tag_table ON post_tag_table.tag_id = tag_table.id
                                WHERE post_tag_table.post_id = post_table.id AND tag_table.name = ?)
                        """;
            }).collect(Collectors.joining(" AND ")));
        }
        return where.toString();
    }

    private List<PostTag> executeQueryGetPostsTags(Long[] postIds) {
        List<Object> args = new ArrayList<>();
        args.add(postIds);
        List<Integer> argTypes = List.of(Types.ARRAY);
        String query = """
                SELECT
                    tag_table.id,
                    post_tag_table.post_id,
                    tag_table.name
                FROM post_tag AS post_tag_table
                LEFT JOIN tag AS tag_table ON post_tag_table.tag_id = tag_table.id
                WHERE post_tag_table.post_id = ANY(?)
                """;

        return jdbcTemplate.query(query, args.toArray(), RepositoryUtil.toIntArray(argTypes), postTagsRowMapper);
    }

    private Optional<Post> executeQueryGetPost(Long postId) {
        List<Object> args = List.of(postId);
        List<Integer> argTypes = List.of(Types.BIGINT);
        String query = """
                SELECT
                    id,
                    title,
                    text,
                    likes_count,
                    comments_count,
                    image_name
                FROM post AS post_table
                WHERE post_table.id = ?
                """;

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query,
                    args.toArray(),
                    RepositoryUtil.toIntArray(argTypes),
                    postRowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private Optional<Long> executeQueryInsertPost(String title, String text) {
        String query = """
                INSERT INTO post (
                    title,
                    text)
                VALUES (?, ?)
                """;

        KeyHolder keyHolder = new GeneratedIdKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, title);
            pst.setString(2, text);
            return pst;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
    }

    private void executeQueryInsertTags(List<String> tags) {
        String query = """
                MERGE INTO tag AS tag_table
                USING (SELECT
                            CAST(? AS VARCHAR) AS source_tag_name) AS source_tags
                ON (tag_table.name = source_tags.source_tag_name)
                WHEN NOT MATCHED THEN
                  INSERT (name)
                  VALUES (source_tags.source_tag_name);
                """;

        jdbcTemplate.batchUpdate(query, tags, tags.size(),
                (ps, tag) -> ps.setString(1, tag));
    }

    private void executeQueryInsertPostTags(Long postId, List<String> tags) {
        String query = """
                MERGE INTO post_tag AS post_tag_table
                USING (SELECT
                            CAST(? AS BIGINT) AS post_id,
                            (SELECT
                                id
                            FROM tag
                            WHERE name = CAST(? AS VARCHAR)) AS tag_id) AS source_tags
                ON (post_tag_table.post_id = source_tags.post_id AND post_tag_table.tag_id = source_tags.tag_id)
                WHEN NOT MATCHED THEN
                  INSERT (post_id, tag_id)
                  VALUES (source_tags.post_id, source_tags.tag_id);
                """;
        jdbcTemplate.batchUpdate(query, tags, tags.size(), (ps, tag) -> {
            ps.setLong(1, postId);
            ps.setString(2, tag);
        });
    }

    private void executeQueryDeletePostTags(Long postId) {
        String query = """
                DELETE FROM post_tag
                WHERE post_id = ?
                """;
        jdbcTemplate.update(query, postId);
    }

    private void executeQueryUpdatePost(Long postId, String title, String text) {
        String query = """
                UPDATE post
                SET
                    title = ?,
                    text = ?,
                    updated_at = ?
                WHERE id = ?
                """;
        jdbcTemplate.update(query, title, text, Timestamp.valueOf(LocalDateTime.now()), postId);
    }

    private void executeQueryDeletePost(Long postId) {
        String query = """
                DELETE FROM post
                WHERE id = ?
                """;
        jdbcTemplate.update(query, postId);
    }

    private void executeQueryIncreasePostLikesCount(Long postId) {
        String query = """
                UPDATE post
                SET likes_count = (likes_count + 1)
                WHERE id = ?
                """;

        jdbcTemplate.update(query, postId);
    }

    private void executeQueryIncreasePostCommentsCount(Long postId) {
        String query = """
                UPDATE post
                SET comments_count = (comments_count + 1)
                WHERE id = ?
                """;

        jdbcTemplate.update(query, postId);
    }

    private void executeQueryDecreasePostCommentsCount(Long postId) {
        String query = """
                UPDATE post
                SET comments_count = CASE
                                         WHEN comments_count < 1 THEN 0
                                         ELSE comments_count - 1
                                     END
                WHERE id = ?
                """;

        jdbcTemplate.update(query, postId);
    }

    private void updatePostTags(Long postId, Post post) {
        List<String> postTags = executeQueryGetPostsTags(new Long[]{postId}).stream().map(PostTag::getName).toList();
        post.setTags(postTags);
    }
}

