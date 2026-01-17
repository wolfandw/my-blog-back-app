package io.github.wolfandw.myblog.backend.itest.service;

import io.github.wolfandw.myblog.backend.itest.AbstractPostIntegrationTest;
import io.github.wolfandw.myblog.backend.model.PostImage;
import io.github.wolfandw.myblog.backend.service.PostImageService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционный тест сервиса {@link PostImageService}
 */
public class PostImageServiceIntegrationTest extends AbstractPostIntegrationTest {
    @Autowired
    private PostImageService postImageService;

    @ParameterizedTest
    @ValueSource(longs = {1L, 14L, 0L, -1L})
    void getPostImageTest(Long postId) {
        PostImage postImage = postImageService.getPostImage(postId);
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
        postImageService.updatePostImage(postId, image);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 14L, 0L, -1L})
    void deletePostImageTest(Long postId) {
        postImageService.deletePostImage(postId);
    }
}
