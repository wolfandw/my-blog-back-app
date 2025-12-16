package io.github.wolfandw.controller.mapper.impl;

import io.github.wolfandw.controller.mapper.PostCommentToDtoMapper;
import io.github.wolfandw.dto.PostCommentResponseDto;
import io.github.wolfandw.model.PostComment;
import org.springframework.stereotype.Component;

/**
 * Реализация {@link PostCommentToDtoMapper}.
 */
@Component
public class PostCommentToDtoMapperImpl implements PostCommentToDtoMapper {
    @Override
    public PostCommentResponseDto mapPostCommentToPostCommentResponseDto(PostComment postComment) {
        return new PostCommentResponseDto(postComment.getId(),
                postComment.getText(),
                postComment.getPostId());
    }
}
