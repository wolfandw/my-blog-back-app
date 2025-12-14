package io.github.wolfandw.test.configuration;

import io.github.wolfandw.repository.PostCommentRepository;
import io.github.wolfandw.repository.PostImageRepository;
import io.github.wolfandw.repository.PostRepository;
import io.github.wolfandw.service.PostCommentService;
import io.github.wolfandw.service.PostImageService;
import io.github.wolfandw.service.PostService;
import io.github.wolfandw.service.impl.PostCommentServiceImpl;
import io.github.wolfandw.service.impl.PostImageServiceImpl;
import io.github.wolfandw.service.impl.PostServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class PostTestConfiguration {
    @Bean
    public PostService postServiceTest(PostRepository postRepository,
                                       PostImageRepository postImageRepository,
                                       PostCommentRepository postCommentRepository) {
        return new PostServiceImpl(postRepository, postImageRepository, postCommentRepository);
    }

    @Bean
    public PostImageService postImageServiceTest(PostImageRepository postImageRepository) {
        return new PostImageServiceImpl(postImageRepository);
    }

    @Bean
    public PostCommentService postCommentServiceTest(PostRepository postRepository,
                                                     PostCommentRepository postCommentRepository) {
        return new PostCommentServiceImpl(postRepository, postCommentRepository);
    }


    @Bean
    public PostRepository postRepositoryTest() {
        return mock(PostRepository.class);
    }

    @Bean
    public PostImageRepository postImageRepositoryTest() {
        return mock(PostImageRepository.class);
    }

    @Bean
    public PostCommentRepository postCommentRepositoryTest() {
        return mock(PostCommentRepository.class);
    }
}
