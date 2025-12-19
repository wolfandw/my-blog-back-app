package io.github.wolfandw.controller.mapper;

import io.github.wolfandw.dto.PostResponseDto;
import io.github.wolfandw.model.Post;

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
