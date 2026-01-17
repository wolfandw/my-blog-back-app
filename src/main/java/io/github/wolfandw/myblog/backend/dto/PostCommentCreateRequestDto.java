package io.github.wolfandw.myblog.backend.dto;

/**
 * DTO-описание запроса на создание комментария поста.
 *
 * @param text   текст нового комменария
 * @param postId идентификатор поста
 */
public record PostCommentCreateRequestDto(String text, Long postId) {
}
