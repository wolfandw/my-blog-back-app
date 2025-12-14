package io.github.wolfandw.controller.mapper;

import io.github.wolfandw.dto.PostsPageResponseDto;
import io.github.wolfandw.model.PostsPage;

public interface PostsPageToDtoMapper {
    PostsPageResponseDto mapPostsPageToPostsPageResponseDto(PostsPage postsPage);
}
