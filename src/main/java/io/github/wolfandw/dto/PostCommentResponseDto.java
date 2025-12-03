package io.github.wolfandw.dto;

public record PostCommentResponseDto(Long id,
                                     String text,
                                     Long postId) {
}
