package io.github.wolfandw.myblog.backend.test;

import io.github.wolfandw.myblog.backend.test.configuration.PostTestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * Абстрактный модульный тест.
 */
@SpringJUnitConfig(classes = PostTestConfiguration.class)
public abstract class AbstractPostTest {
}
