package io.github.wolfandw.dto;

import java.util.List;

/**
 * DTO-описание запроса на обновление поста.
 *
 * @param id    идентификатор поста
 * @param title новый заголовок поста
 * @param text  новый текст поста
 * @param tags  новый список тэгов поста
 */
public record PostUpdateRequestDto(Long id,
                                   String title,
                                   String text,
                                   List<String> tags) {
}
