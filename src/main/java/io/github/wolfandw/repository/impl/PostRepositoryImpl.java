package io.github.wolfandw.repository.impl;

import io.github.wolfandw.model.Post;
import io.github.wolfandw.model.PostTag;
import io.github.wolfandw.repository.PostRepository;
import io.github.wolfandw.repository.mapper.PostRowMapper;
import io.github.wolfandw.repository.mapper.PostTagsRowMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PostRowMapper postRowMapper;
    private final PostTagsRowMapper postTagsRowMapper;

    public PostRepositoryImpl(JdbcTemplate jdbcTemplate, PostRowMapper postRowMapper, PostTagsRowMapper postTagsRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.postRowMapper = postRowMapper;
        this.postTagsRowMapper = postTagsRowMapper;
    }

    @Override
    public List<Post> getPosts(List<String> searchWords, List<String> tags, int pageNumber, int pageSize) {
        List<Post> posts = queryPosts(searchWords, tags, pageNumber, pageSize);
        Long[] postIds = posts.stream().map(Post::getId).toArray(Long[]::new);
        List<PostTag> postTags = queryPostsTags(postIds);
        Map<Long, List<String>> postTagsMap = postTags.stream().collect(Collectors.groupingBy(PostTag::getPostId, Collectors.mapping(PostTag::getName, Collectors.toList())));
        posts.forEach(p -> p.setTags(postTagsMap.getOrDefault(p.getId(), List.of())));
        return posts;
    }

    @Override
    public Optional<Post> getPost(Long postId) {
        Optional<Post> post = queryPost(postId);
        post.ifPresent(p -> {
            List<String> postTags = queryPostsTags(new Long[]{postId}).stream().map(PostTag::getName).toList();
            p.setTags(postTags);
        });
        return post;
    }


    @Override
    public Optional<Post> createPost(String title, String text, List<String> tags) {
        return Optional.empty();
    }

    @Override
    public Optional<Post> updatePost(Long id, String title, String text, List<String> tags) {
        return Optional.empty();
    }

    @Override
    public void deletePost(Long id) {
    }

    @Override
    public int increaseLikesCount(Long id) {
        return 0;
    }

    @Override
    public void increaseCommentCount(Long id) {
    }

    @Override
    public void decreaseCommentCount(Long id) {
    }

    @Override
    public int getPostsCount(List<String> searchWords, List<String> tags) {
        Optional<Integer> postsCount = queryPostsCount(searchWords, tags);
        return postsCount.orElse(0);
    }

    @Override
    public void setImage(Long postId, String imageName) {
    }

    @Override
    public Optional<String> getImage(Long postId) {
        return getPost(postId).map(Post::getImage);
    }

    private List<Post> queryPosts(List<String> searchWords, List<String> tags, int pageNumber, int pageSize) {
        List<Object> args = new ArrayList<>();
        List<Integer> argTypes = new ArrayList<>();
        String where = buildPostsQueryWhere(searchWords, tags, args, argTypes);
        String query = """
                SELECT
                    id,
                    title,
                    text,
                    likes_count,
                    comments_count,
                    image
                FROM post AS post_table
                """ +
                where +
                " ORDER BY post_table.created_at DESC LIMIT ? OFFSET ?";

        args.add(pageSize);
        argTypes.add(Types.INTEGER);

        args.add((pageNumber - 1) * pageSize);
        argTypes.add(Types.INTEGER);

        return jdbcTemplate.query(query, args.toArray(), toIntArray(argTypes), postRowMapper);
    }

    private Optional<Integer> queryPostsCount(List<String> searchWords, List<String> tags) {
        List<Object> args = new ArrayList<>();
        List<Integer> argTypes = new ArrayList<>();
        String where = buildPostsQueryWhere(searchWords, tags, args, argTypes);
        String query = """
                SELECT
                    COUNT(*)
                FROM post AS post_table
                """ +
                where;
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, args.toArray(), toIntArray(argTypes), Integer.class));
    }

    private String buildPostsQueryWhere(List<String> searchWords, List<String> tags, List<Object> args, List<Integer> argTypes) {
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

    private List<PostTag> queryPostsTags(Long[] postIds) {
        List<Object> args = new ArrayList<>();
        args.add(postIds);
        List<Integer> argTypes = List.of(Types.ARRAY);
        String query = """
                SELECT
                    post_tag_table.post_id,
                    tag_table.name
                FROM post_tag AS post_tag_table
                LEFT JOIN tag AS tag_table ON post_tag_table.tag_id = tag_table.id
                WHERE post_tag_table.post_id = ANY(?)
                """;
        return jdbcTemplate.query(query, args.toArray(), toIntArray(argTypes), postTagsRowMapper);
    }

    private Optional<Post> queryPost(Long postId) {
        List<Object> args = List.of(postId);
        List<Integer> argTypes = List.of(Types.BIGINT);
        String query = """
                SELECT
                    id,
                    title,
                    text,
                    likes_count,
                    comments_count,
                    image
                FROM post AS post_table
                WHERE post_table.id = ?
                """;

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, args.toArray(), toIntArray(argTypes), postRowMapper));
    }

    private int[] toIntArray(List<Integer> integers) {
        return integers.stream().mapToInt(Integer::intValue).toArray();
    }
}

