package io.github.wolfandw.myblog.backend.test;

import io.github.wolfandw.myblog.backend.repository.PostCommentRepository;
import io.github.wolfandw.myblog.backend.repository.PostImageRepository;
import io.github.wolfandw.myblog.backend.repository.PostRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockReset;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * Абстрактный модульный тест.
 */
@SpringBootTest
public abstract class AbstractPostTest {
    @MockitoBean(reset = MockReset.BEFORE)
    protected PostRepository postRepository;

    @MockitoBean(reset = MockReset.BEFORE)
    protected PostCommentRepository postCommentRepository;

    @MockitoBean(reset = MockReset.BEFORE)
    protected PostImageRepository postImageRepository;
}
