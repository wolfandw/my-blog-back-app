package io.github.wolfandw.repository.mapper;

import io.github.wolfandw.model.PostTag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер результатов запросов в тэги постов.
 */
@Component
public class PostTagsRowMapper implements RowMapper<PostTag> {
    @Override
    public PostTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PostTag(
                rs.getLong("id"),
                rs.getLong("post_id"),
                rs.getString("name"));
    }
}
