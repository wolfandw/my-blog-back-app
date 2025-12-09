package io.github.wolfandw.repository.mapper;

import io.github.wolfandw.model.Post;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class PostRowMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Post(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("text"),
                new ArrayList<>(),
                rs.getInt("likes_count"),
                rs.getInt("comments_count"),
                rs.getString("image"));
    }
}
