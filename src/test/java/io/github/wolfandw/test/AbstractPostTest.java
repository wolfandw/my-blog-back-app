package io.github.wolfandw.test;

import io.github.wolfandw.test.configuration.PostTestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * Абстрактный модульный тест.
 */
@SpringJUnitConfig(classes = PostTestConfiguration.class)
public abstract class AbstractPostTest {
}
