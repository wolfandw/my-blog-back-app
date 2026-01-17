package io.github.wolfandw.myblog.backend.repository.impl;

import io.github.wolfandw.myblog.backend.model.PostComment;
import io.github.wolfandw.myblog.backend.repository.PostCommentRepository;
import io.github.wolfandw.myblog.backend.repository.PostRepository;
import io.github.wolfandw.myblog.backend.repository.mapper.PostCommentRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link PostCommentRepository}
 */
@Repository("postCommentRepository")
public class PostCommentRepositoryImpl implements PostCommentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PostRepository postRepository;
    private final PostCommentRowMapper postCommentRowMapper;

    /**
     * Создает репозиторий для работы с комментариев постов.
     *
     * @param jdbcTemplate         {@link JdbcTemplate}
     * @param postRepository       репозиторий постов
     * @param postCommentRowMapper маппер результатов запроса в комментарии постов
     */
    public PostCommentRepositoryImpl(JdbcTemplate jdbcTemplate,
                                     PostRepository postRepository,
                                     PostCommentRowMapper postCommentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.postRepository = postRepository;
        this.postCommentRowMapper = postCommentRowMapper;
    }

    @Override
    public List<PostComment> getPostComments(Long postId) {
        return executeQueryGetPostComments(postId);
    }

    @Override
    public Optional<PostComment> getPostComment(Long postId, Long commentId) {
        return executeQueryGetPostComment(postId, commentId);
    }

    @Override
    public Optional<PostComment> createPostComment(Long postId, String text) {
        Optional<Long> newCommentId = executeQueryInsertComment(postId, text);
        Optional<PostComment> comment = newCommentId
                .flatMap(commentId -> executeQueryGetPostComment(postId, commentId));
        comment.ifPresent(c -> postRepository.increasePostCommentCount(postId));
        return comment;
    }


    @Override
    public Optional<PostComment> updatePostComment(Long postId, Long commentId, String text) {
        executeQueryUpdatePostComment(postId, commentId, text);
        return getPostComment(postId, commentId);
    }

    @Override
    public void deletePostComment(Long postId, Long commentId) {
        executeQueryDeletePostComments(postId, commentId);
        postRepository.decreasePostCommentCount(postId);
    }

    @Override
    public void deletePostComments(Long postId) {
        executeQueryDeletePostComments(postId);
    }

    private List<PostComment> executeQueryGetPostComments(Long postId) {
        List<Object> args = List.of(postId);
        List<Integer> argTypes = List.of(Types.BIGINT);
        String query = """
                SELECT
                    id,
                    text,
                    post_id
                FROM comment AS comment_table
                WHERE comment_table.post_id = ?
                ORDER BY comment_table.created_at ASC
                """;

        return jdbcTemplate.query(query, args.toArray(), RepositoryUtil.toIntArray(argTypes), postCommentRowMapper);
    }

    private Optional<PostComment> executeQueryGetPostComment(Long postId, Long commentId) {
        List<Object> args = List.of(postId, commentId);
        List<Integer> argTypes = List.of(Types.BIGINT, Types.BIGINT);
        String query = """
                SELECT
                    id,
                    text,
                    post_id
                FROM comment AS comment_table
                WHERE comment_table.post_id = ? AND comment_table.id = ?
                ORDER BY comment_table.created_at DESC
                """;

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query,
                    args.toArray(),
                    RepositoryUtil.toIntArray(argTypes),
                    postCommentRowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private Optional<Long> executeQueryInsertComment(Long postId, String text) {
        String query = """
                INSERT INTO comment (
                    text,
                    post_id)
                VALUES (?, ?)
                """;

        KeyHolder keyHolder = new GeneratedIdKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, text);
            pst.setLong(2, postId);
            return pst;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
    }

    private void executeQueryUpdatePostComment(Long postId, Long commentId, String text) {
        String query = """
                UPDATE comment
                SET
                    text = ?,
                    updated_at = ?
                WHERE post_id = ? AND id = ?
                """;

        jdbcTemplate.update(query, text, Timestamp.valueOf(LocalDateTime.now()), postId, commentId);
    }

    private void executeQueryDeletePostComments(Long postId, Long commentId) {
        String query = """
                DELETE FROM comment
                WHERE post_id = ? AND id = ?
                """;
        jdbcTemplate.update(query, postId, commentId);
    }

    private void executeQueryDeletePostComments(Long postId) {
        String query = """
                DELETE FROM comment
                WHERE post_id = ?
                """;
        jdbcTemplate.update(query, postId);
    }
}
