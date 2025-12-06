package io.github.wolfandw.controller.mapper;

import io.github.wolfandw.dto.PostResponseDto;
import io.github.wolfandw.model.Post;

public interface PostToDtoMapper {
    PostResponseDto mapPostToPostResponseDto(Post post);
}
