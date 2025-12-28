package io.github.wolfandw.myblog.backend.test.configuration;

import io.github.wolfandw.myblog.backend.repository.PostCommentRepository;
import io.github.wolfandw.myblog.backend.repository.PostImageRepository;
import io.github.wolfandw.myblog.backend.repository.PostRepository;
import io.github.wolfandw.myblog.backend.service.FileStorageService;
import io.github.wolfandw.myblog.backend.service.PostCommentService;
import io.github.wolfandw.myblog.backend.service.PostImageService;
import io.github.wolfandw.myblog.backend.service.PostService;
import io.github.wolfandw.myblog.backend.service.impl.FileStorageServiceImpl;
import io.github.wolfandw.myblog.backend.service.impl.PostCommentServiceImpl;
import io.github.wolfandw.myblog.backend.service.impl.PostImageServiceImpl;
import io.github.wolfandw.myblog.backend.service.impl.PostServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import static org.mockito.Mockito.mock;

/**
 * Тестовая конфигурация для модульных тестов.
 */
@TestConfiguration
public class PostTestConfiguration {
    /**
     * Создает тестовый сервис постов.
     *
     * @param postRepositoryTest        mock-репозиторий постов
     * @param postImageServiceTest      mock-сервис картинок постов
     * @param postCommentRepositoryTest mock-репозиторий комментариев постов
     * @return тестовый сервис постов
     */
    @Bean
    public PostService postServiceTest(PostRepository postRepositoryTest,
                                       PostImageService postImageServiceTest,
                                       PostCommentRepository postCommentRepositoryTest) {
        return new PostServiceImpl(postRepositoryTest, postImageServiceTest, postCommentRepositoryTest);
    }

    /**
     * Создает тестовый сервис картинок постов.
     *
     * @param postImageRepositoryTest mock-репозиторий картинок постов
     * @return тестовый сервис картинок постов
     */
    @Bean
    public PostImageService postImageServiceTest(PostImageRepository postImageRepositoryTest, FileStorageService fileStorageServiceTest) {
        return new PostImageServiceImpl(postImageRepositoryTest, fileStorageServiceTest);
    }

    /**
     * Создает тестовый сервис комментариев постов.
     *
     * @param postRepositoryTest        mock-репозиторий постов
     * @param postCommentRepositoryTest mock-репозиторий комментариев постов
     * @return тестовый сервис комментариев постов
     */
    @Bean
    public PostCommentService postCommentServiceTest(PostRepository postRepositoryTest,
                                                     PostCommentRepository postCommentRepositoryTest) {
        return new PostCommentServiceImpl(postRepositoryTest, postCommentRepositoryTest);
    }

    /**
     * Создает тестовый сервис по работе с файлами.
     *
     * @return тестовый сервис по работе с файлами
     */
    @Bean
    @Order
    public FileStorageService fileStorageServiceTest() {
        return new FileStorageServiceImpl("upload/posts/");
    }

    /**
     * Создает mock-репозиторий постов.
     *
     * @return mock-репозиторий постов
     */
    @Bean
    public PostRepository postRepositoryTest() {
        return mock(PostRepository.class);
    }

    /**
     * Создает mock-репозиторий картинок постов.
     *
     * @return mock-репозиторий картинок постов
     */
    @Bean
    public PostImageRepository postImageRepositoryTest() {
        return mock(PostImageRepository.class);
    }

    /**
     * Создает mock-репозиторий комментариев постов.
     *
     * @return mock-репозиторий комментариев постов
     */
    @Bean
    public PostCommentRepository postCommentRepositoryTest() {
        return mock(PostCommentRepository.class);
    }
}
