package io.github.wolfandw.myblog.backend.test.service;

import io.github.wolfandw.myblog.backend.model.PostImage;
import io.github.wolfandw.myblog.backend.repository.PostImageRepository;
import io.github.wolfandw.myblog.backend.service.PostImageService;
import io.github.wolfandw.myblog.backend.test.AbstractPostTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Модульный тест картинок постов.
 */
public class PostImageServiceTest extends AbstractPostTest {
    @Autowired
    @Qualifier("postImageServiceTest")
    private PostImageService postImageServiceTest;

    @Autowired
    @Qualifier("postImageRepositoryTest")
    private PostImageRepository postImageRepositoryTest;

    private Map<Long, PostImage> images = new HashMap<>();

    @BeforeEach
    void setUp() {
        reset(postImageRepositoryTest);

        images = LongStream.range(1, 16).boxed().collect(HashMap::new,
                (m, postId) -> m.put(postId,
                        new PostImage(new byte[0], MediaType.APPLICATION_OCTET_STREAM, postId)),
                HashMap::putAll);
    }

    @Test
    void testGetPostImage() {
        Long postId = 5L;
        PostImage mockPostImage = images.get(postId);
        Optional<String> mockPostImageName = Optional.of(postId + ".png");
        when(postImageRepositoryTest.getPostImageName(postId)).thenReturn(mockPostImageName);
        PostImage postImage = postImageServiceTest.getPostImage(postId);

        assertNotNull(postImage, "Картинка поста должна быть получена");
        assertArrayEquals(mockPostImage.getData(), postImage.getData(), "Данные картинки должны быть равны исходным");
        assertEquals(mockPostImage.getMediaType(), postImage.getMediaType(), "Тип данных картинки должен быть равен исходному");
        assertEquals(mockPostImage.getPostId(), postImage.getPostId(), "Идентификатор поста картинки должен быть равен исходному");
    }

    @Test
    void testUpdatePostImage() {
        Long postId = 5L;
        String mockPostImageName = postId + ".jpg";
        MockMultipartFile multipartFile = new MockMultipartFile(
                "image",
                mockPostImageName,
                MediaType.IMAGE_JPEG_VALUE,
                new byte[0]
        );
        doNothing().when(postImageRepositoryTest).updatePostImageName(postId, mockPostImageName);

        postImageServiceTest.updatePostImage(postId, multipartFile);

        verify(postImageRepositoryTest).updatePostImageName(postId, mockPostImageName);
    }
}
