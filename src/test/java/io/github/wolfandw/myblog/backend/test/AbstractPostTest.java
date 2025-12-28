package io.github.wolfandw.myblog.backend.test;

import io.github.wolfandw.myblog.backend.test.configuration.PostTestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Абстрактный модульный тест.
 */
@SpringBootTest
@Import(PostTestConfiguration.class)
public abstract class AbstractPostTest {
}
