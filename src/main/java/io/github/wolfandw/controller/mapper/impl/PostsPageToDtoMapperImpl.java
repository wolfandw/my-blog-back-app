package io.github.wolfandw.controller.mapper.impl;

import io.github.wolfandw.controller.mapper.PostToDtoMapper;
import io.github.wolfandw.controller.mapper.PostsPageToDtoMapper;
import io.github.wolfandw.dto.PostsPageResponseDto;
import io.github.wolfandw.model.PostsPage;
import org.springframework.stereotype.Component;

@Component
public class PostsPageToDtoMapperImpl implements PostsPageToDtoMapper {
    private final PostToDtoMapper postToDtoMapper;

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
