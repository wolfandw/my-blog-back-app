package io.github.wolfandw.dto;

/**
 * DTO-описание комментария поста.
 *
 * @param id     идентификатор комментария поста
 * @param text   текст комментария поста
 * @param postId идентификатор поста
 */
public record PostCommentResponseDto(Long id, String text, Long postId) {
}
