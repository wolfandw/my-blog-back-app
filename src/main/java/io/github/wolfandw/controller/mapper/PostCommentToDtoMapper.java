package io.github.wolfandw.controller.mapper;

import io.github.wolfandw.dto.PostCommentResponseDto;
import io.github.wolfandw.model.PostComment;

public interface PostCommentToDtoMapper {
    PostCommentResponseDto mapPostCommentToPostCommentResponseDto(PostComment postComment);
}
