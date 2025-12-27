package io.github.wolfandw.myblog.backend.controller.mapper;

import io.github.wolfandw.myblog.backend.dto.PostCommentResponseDto;
import io.github.wolfandw.myblog.backend.model.PostComment;

/**
 * Маппер комментария поста в DTO описание комментария поста.
 */
public interface PostCommentToDtoMapper {
    /**
     * Преобразует комментарий постав DTO комментария поста.
     *
     * @param postComment комментарий поста
     * @return DTO комментария поста
     */
    PostCommentResponseDto mapPostCommentToPostCommentResponseDto(PostComment postComment);
}
