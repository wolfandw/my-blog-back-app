package io.github.wolfandw.service;

import io.github.wolfandw.dto.PostCommentCreateRequestDto;
import io.github.wolfandw.dto.PostCommentResponseDto;
import io.github.wolfandw.dto.PostCommentUpdateRequestDto;

import java.util.List;

public interface PostCommentService {
    List<PostCommentResponseDto> getComments(Long postId);

    PostCommentResponseDto getComment(Long postId, Long commentId);

    PostCommentResponseDto createComment(Long postId, PostCommentCreateRequestDto commentRequest);

    PostCommentResponseDto updateComment(Long postId, Long commentId, PostCommentUpdateRequestDto commentRequest);

    void deleteComment(Long postId, Long commentId);
}
