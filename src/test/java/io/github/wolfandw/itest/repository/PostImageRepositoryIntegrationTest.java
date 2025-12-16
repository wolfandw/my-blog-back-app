package io.github.wolfandw.itest.repository;

import io.github.wolfandw.itest.AbstractPostIntegrationTest;
import io.github.wolfandw.model.PostImage;
import io.github.wolfandw.repository.PostImageRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционный тест репозитория {@link PostImageRepository}
 */
public class PostImageRepositoryIntegrationTest extends AbstractPostIntegrationTest {
    @Autowired
    private PostImageRepository postImageRepository;

    @ParameterizedTest
    @ValueSource(longs = {1L, 14L, 0L, -1L})
    void getPostImageTest(Long postId) {
        PostImage postImage = postImageRepository.getPostImage(postId);
        assertNotNull(postImage, "Картинка поста не должна быть нулл");
        assertEquals(postId, postImage.getPostId(), "Картинка должна содержать идентификатор поста");
        assertArrayEquals(new byte[0], postImage.getData(), "Данные картинки должны совпадать");
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 14L, 0L, -1L})
    void updatePostImageTest(Long postId) {
        MockMultipartFile image = new MockMultipartFile("image",
                "image-file-name.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[]{1, 2, 3});
        postImageRepository.updatePostImage(postId, image);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 14L, 0L, -1L})
    void deletePostImageTest(Long postId) {
        postImageRepository.deletePostImage(postId);
    }
}
