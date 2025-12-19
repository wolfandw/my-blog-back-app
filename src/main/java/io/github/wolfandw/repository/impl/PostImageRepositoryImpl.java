package io.github.wolfandw.repository.impl;

import io.github.wolfandw.model.Post;
import io.github.wolfandw.repository.PostImageRepository;
import io.github.wolfandw.repository.PostRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Реализация {@link PostImageRepository}
 */
@Repository
public class PostImageRepositoryImpl implements PostImageRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PostRepository postRepository;

    /**
     * Создание репозитория для работы с картинками постов.
     *
     * @param jdbcTemplate   {@link JdbcTemplate}
     * @param postRepository репозиторий постов
     */
    public PostImageRepositoryImpl(JdbcTemplate jdbcTemplate, PostRepository postRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.postRepository = postRepository;
    }

    @Override
    public void updatePostImageName(Long postId, String imageName) {
        executeQueryUpdatePostImageName(postId, imageName);
    }

    @Override
    public Optional<String> getPostImageName(Long postId) {
        return postRepository.getPost(postId).map(Post::getImageName);
    }

    private void executeQueryUpdatePostImageName(Long postId, String imageName) {
        String query = """
                UPDATE post
                SET
                    image_name = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(query, imageName, postId);
    }
}
