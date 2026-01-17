package io.github.wolfandw.myblog.backend.dto;

import java.util.List;

/**
 * DTO-описание постаю
 *
 * @param id            идентификатор поста
 * @param title         заголовок поста
 * @param text          текст поста
 * @param tags          тэги поста
 * @param likesCount    количество лайков поста
 * @param commentsCount количество комментариев поста
 */
public record PostResponseDto(Long id,
                              String title,
                              String text,
                              List<String> tags,
                              int likesCount,
                              int commentsCount) {
}
