package io.github.wolfandw.test.configuration;

import io.github.wolfandw.repository.PostCommentRepository;
import io.github.wolfandw.repository.PostImageRepository;
import io.github.wolfandw.repository.PostRepository;
import io.github.wolfandw.service.FileStorageService;
import io.github.wolfandw.service.PostCommentService;
import io.github.wolfandw.service.PostImageService;
import io.github.wolfandw.service.PostService;
import io.github.wolfandw.service.impl.FileStorageServiceImpl;
import io.github.wolfandw.service.impl.PostCommentServiceImpl;
import io.github.wolfandw.service.impl.PostImageServiceImpl;
import io.github.wolfandw.service.impl.PostServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * Тестовая конфигурация для модульных тестов.
 */
@Configuration
public class PostTestConfiguration {
    /**
     * Создает тестовый сервис постов.
     *
     * @param postRepository        mock-репозиторий постов
     * @param postImageService      mock-сервис картинок постов
     * @param postCommentRepository mock-репозиторий комментариев постов
     * @return тестовый сервис постов
     */
    @Bean
    public PostService postServiceTest(PostRepository postRepository,
                                       PostImageService postImageService,
                                       PostCommentRepository postCommentRepository) {
        return new PostServiceImpl(postRepository, postImageService, postCommentRepository);
    }

    /**
     * Создает тестовый сервис картинок постов.
     *
     * @param postImageRepository mock-репозиторий картинок постов
     * @return тестовый сервис картинок постов
     */
    @Bean
    public PostImageService postImageServiceTest(PostImageRepository postImageRepository, FileStorageService fileStorageService) {
        return new PostImageServiceImpl(postImageRepository, fileStorageService);
    }

    /**
     * Создает тестовый сервис комментариев постов.
     *
     * @param postRepository        mock-репозиторий постов
     * @param postCommentRepository mock-репозиторий комментариев постов
     * @return тестовый сервис комментариев постов
     */
    @Bean
    public PostCommentService postCommentServiceTest(PostRepository postRepository,
                                                     PostCommentRepository postCommentRepository) {
        return new PostCommentServiceImpl(postRepository, postCommentRepository);
    }

    /**
     * Создает тестовый сервис по работе с файлами.
     *
     * @return тестовый сервис по работе с файлами
     */
    @Bean
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
