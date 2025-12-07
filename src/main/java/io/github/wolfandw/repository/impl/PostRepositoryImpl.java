package io.github.wolfandw.repository.impl;

import io.github.wolfandw.model.Post;
import io.github.wolfandw.repository.PostRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> getPosts(String search, int pageNumber, int pageSize) {
        return new ArrayList<>();
    }

    @Override
    public Optional<Post> getPost(Long id) {
        return Optional.empty();
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
    public int getPostsCount() {
        return 0;
    }

    @Override
    public void setImage(Long postId, String imageName) {
    }

    @Override
    public Optional<String> getImage(Long postId) {
        return Optional.empty();
    }
}

