package io.github.wolfandw.myblog.backend.controller.mapper.impl;

import io.github.wolfandw.myblog.backend.controller.mapper.PostToDtoMapper;
import io.github.wolfandw.myblog.backend.controller.mapper.PostsPageToDtoMapper;
import io.github.wolfandw.myblog.backend.dto.PostsPageResponseDto;
import io.github.wolfandw.myblog.backend.model.PostsPage;
import org.springframework.stereotype.Component;

/**
 * Реализация {@link PostsPageToDtoMapper}.
 */
@Component
public class PostsPageToDtoMapperImpl implements PostsPageToDtoMapper {
    private final PostToDtoMapper postToDtoMapper;

    /**
     * Создание маппера страницы постов.
     *
     * @param postToDtoMapper маппер постов
     */
    public PostsPageToDtoMapperImpl(PostToDtoMapper postToDtoMapper) {
        this.postToDtoMapper = postToDtoMapper;
    }

    @Override
    public PostsPageResponseDto mapPostsPageToPostsPageResponseDto(PostsPage postsPage) {
        return new PostsPageResponseDto(postsPage.getPosts().stream()
                .map(postToDtoMapper::mapPostToPostResponseDto).toList(),
                postsPage.hasPrev(),
                postsPage.hasNext(),
                postsPage.getLastPage());
    }
}
