package io.github.wolfandw.repository.mapper;

import io.github.wolfandw.model.PostComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер результатов запроса в комментарии постов.
 */
@Component
public class PostCommentRowMapper implements RowMapper<PostComment> {
    @Override
    public PostComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PostComment(rs.getLong("id"),
                rs.getString("text"),
                rs.getLong("post_id"));
    }
}
