package io.github.wolfandw.dto;

/**
 * DTO-описание запроса на создание комментария поста.
 *
 * @param text   текст нового комменария
 * @param postId идентификатор поста
 */
public record PostCommentCreateRequestDto(String text, Long postId) {
}
