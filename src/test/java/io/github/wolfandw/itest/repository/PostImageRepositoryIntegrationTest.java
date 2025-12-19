package io.github.wolfandw.itest.repository;

import io.github.wolfandw.itest.AbstractPostIntegrationTest;
import io.github.wolfandw.repository.PostImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Интеграционный тест репозитория {@link PostImageRepository}
 */
public class PostImageRepositoryIntegrationTest extends AbstractPostIntegrationTest {
    @Autowired
    private PostImageRepository postImageRepository;

    @Test
    void updatePostImageNameTest() {
        Optional<String> imageNameBefore = postImageRepository.getPostImageName(13L);
        assertTrue(imageNameBefore.isPresent(), "Имя картинки поста до изменения должно быть");
        assertEquals("13.png", imageNameBefore.get(), "Имя картинки должно совпадать");

        postImageRepository.updatePostImageName(13L, "133.jpg");

        Optional<String> imageName = postImageRepository.getPostImageName(13L);
        assertTrue(imageName.isPresent(), "Имя картинки поста после изменения должен быть");
        assertEquals("133.jpg", imageName.get(), "Новое имя картинки должно быть установлено");
    }

    @Test
    void getPostImageNameTest() {
        Optional<String> imageName = postImageRepository.getPostImageName(13L);

        assertTrue(imageName.isPresent(), "Имя картинки поста должно быть");
        assertEquals("13.png", imageName.get(), "Имя картинки должно совпадать");
    }

    @Test
    void deletePostImageNameTest() {
        Optional<String> imageNameBefore = postImageRepository.getPostImageName(13L);
        assertTrue(imageNameBefore.isPresent(), "Имя картинки поста до изменения должно быть");
        assertEquals("13.png", imageNameBefore.get(), "Имя картинки должно совпадать");

        postImageRepository.updatePostImageName(13L, null);

        Optional<String> imageName = postImageRepository.getPostImageName(13L);
        assertTrue(imageName.isEmpty(), "Имя картинки поста после изменения должен быть пустое");
    }
}
