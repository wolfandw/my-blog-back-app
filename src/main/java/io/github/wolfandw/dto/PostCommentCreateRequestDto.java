package io.github.wolfandw.dto;

public record PostCommentCreateRequestDto(String text,
                                          Long postId) {
}
