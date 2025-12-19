package io.github.wolfandw.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;

/**
 * Конфигурация настройки {@link HttpMessageConverter}.
 */
@Configuration
public class RestConfiguration {
    /**
     * Создает реализацию {@link HttpMessageConverter}.
     *
     * @return реализация {@link HttpMessageConverter}
     */
    @Bean
    public HttpMessageConverter<Object> httpMessageConverter() {
        return new JacksonJsonHttpMessageConverter();
    }
}
