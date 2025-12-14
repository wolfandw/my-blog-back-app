package io.github.wolfandw.test.service;

import io.github.wolfandw.model.PostImage;
import io.github.wolfandw.repository.PostImageRepository;
import io.github.wolfandw.service.PostImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostImageServiceTest extends AbstractPostServiceTest {
    @Autowired
    private PostImageService postImageService;

    @Autowired
    private PostImageRepository postImageRepository;

    @Mock
    private MultipartFile multipartFile;

    private Map<Long, PostImage> images = new HashMap<>();

    @BeforeEach
    void setUp() {
        reset(postImageRepository);

        images = LongStream.range(1, 16).boxed().collect(HashMap::new,
                (m, postId) -> m.put(postId,
                        new PostImage(new byte[]{postId.byteValue()}, MediaType.IMAGE_PNG, postId)),
                HashMap::putAll);
    }

    @Test
    void testGetPostImage() {
        Long postId = 5L;
        PostImage mockPostImage = images.get(postId);
        when(postImageRepository.getPostImage(postId)).thenReturn(mockPostImage);
        PostImage postImage = postImageService.getPostImage(postId);

        assertNotNull(postImage, "Картинка поста должна быть получена");
        assertArrayEquals(mockPostImage.getData(), postImage.getData(), "Данные картинки должны быть равны исходным");
        assertEquals(mockPostImage.getMediaType(), postImage.getMediaType(), "Тип данных картинки должен быть равен исходному");
        assertEquals(mockPostImage.getPostId(), postImage.getPostId(), "Идентификатор поста картинки должен быть равен исходному");
    }

    @Test
    void testUpdatePostImage() {
        Long postId = 5L;
        doNothing().when(postImageRepository).updatePostImage(postId, multipartFile);

        postImageService.updatePostImage(postId, multipartFile);

        verify(postImageRepository).updatePostImage(postId, multipartFile);
    }
}
