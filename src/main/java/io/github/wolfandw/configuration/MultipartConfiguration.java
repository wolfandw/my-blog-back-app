package io.github.wolfandw.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/**
 * Конфигурация настройки {@link MultipartResolver}.
 */
@Configuration
public class MultipartConfiguration {
    /**
     * Создает стандартную реализацию интерфейса {@link MultipartResolver}.
     *
     * @return реализация интерфейса {@link MultipartResolver}
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
