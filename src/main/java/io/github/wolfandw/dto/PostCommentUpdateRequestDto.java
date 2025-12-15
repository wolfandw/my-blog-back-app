package io.github.wolfandw.dto;

public record PostCommentUpdateRequestDto(Long id, String text, Long postId) {
}
