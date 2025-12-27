package io.github.wolfandw.myblog.backend.controller.mapper;

import io.github.wolfandw.myblog.backend.dto.PostResponseDto;
import io.github.wolfandw.myblog.backend.model.Post;

/**
 * Маппер поста в DTO поста.
 */
public interface PostToDtoMapper {
    /**
     * Преобразует пост в DTO-описание поста.
     *
     * @param post пост
     * @return DTO-описание поста
     */
    PostResponseDto mapPostToPostResponseDto(Post post);
}
