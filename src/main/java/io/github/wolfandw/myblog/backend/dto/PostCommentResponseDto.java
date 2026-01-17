package io.github.wolfandw.myblog.backend.dto;

/**
 * DTO-описание комментария поста.
 *
 * @param id     идентификатор комментария поста
 * @param text   текст комментария поста
 * @param postId идентификатор поста
 */
public record PostCommentResponseDto(Long id, String text, Long postId) {
}
