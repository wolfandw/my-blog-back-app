package io.github.wolfandw.controller.mapper.impl;

import io.github.wolfandw.controller.mapper.PostToDtoMapper;
import io.github.wolfandw.dto.PostResponseDto;
import io.github.wolfandw.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostToDtoMapperImpl implements PostToDtoMapper {
    public PostResponseDto mapPostToPostResponseDto(Post post) {
        return new PostResponseDto(post.getId(), post.getTitle(), post.getText(), post.getTags(), post.getLikesCount(), post.getCommentsCount());
    }
}
