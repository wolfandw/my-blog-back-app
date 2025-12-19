package io.github.wolfandw.controller.mapper;

import io.github.wolfandw.dto.PostCommentResponseDto;
import io.github.wolfandw.model.PostComment;

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
