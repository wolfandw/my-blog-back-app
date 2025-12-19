package io.github.wolfandw.itest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Основная онфигурация для интеграционных тестов.
 */
@Configuration
@EnableWebMvc
@ComponentScan({
        "io.github.wolfandw.controller",
        "io.github.wolfandw.repository",
        "io.github.wolfandw.service"
})
public class WebConfigurationIntegrationTest {
}
