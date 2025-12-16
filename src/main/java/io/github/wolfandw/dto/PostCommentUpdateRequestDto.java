package io.github.wolfandw.dto;

/**
 * DTO-описание запроса на обновление комментария поста.
 *
 * @param id     идентификатор комментария поста
 * @param text   текст комментария поста
 * @param postId идентификатор поста
 */
public record PostCommentUpdateRequestDto(Long id, String text, Long postId) {
}
