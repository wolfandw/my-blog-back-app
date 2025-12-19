package io.github.wolfandw.controller.mapper;

import io.github.wolfandw.dto.PostsPageResponseDto;
import io.github.wolfandw.model.PostsPage;

/**
 * Маппер страницы поста в DTO страницы поста.
 */
public interface PostsPageToDtoMapper {
    /**
     * Преобразует страницу постов в DTO-описание страницы постов.
     *
     * @param postsPage страница постов
     * @return DTO-описание страницы постов
     */
    PostsPageResponseDto mapPostsPageToPostsPageResponseDto(PostsPage postsPage);
}
