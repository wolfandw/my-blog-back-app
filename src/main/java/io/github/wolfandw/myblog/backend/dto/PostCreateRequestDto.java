package io.github.wolfandw.myblog.backend.dto;

import java.util.List;

/**
 * DTO-описание запроса на создание поста.
 *
 * @param title заголовок нового поста
 * @param text  текст новгого поста
 * @param tags  тэги нового поста
 */
public record PostCreateRequestDto(String title, String text, List<String> tags) {
}
