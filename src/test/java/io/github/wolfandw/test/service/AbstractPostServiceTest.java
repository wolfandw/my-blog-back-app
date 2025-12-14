package io.github.wolfandw.test.service;

import io.github.wolfandw.test.configuration.PostTestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = PostTestConfiguration.class)
public abstract class AbstractPostServiceTest {
}
