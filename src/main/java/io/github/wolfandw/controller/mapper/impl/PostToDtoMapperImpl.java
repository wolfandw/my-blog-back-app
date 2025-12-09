package io.github.wolfandw.controller.mapper.impl;

import io.github.wolfandw.controller.mapper.PostToDtoMapper;
import io.github.wolfandw.dto.PostResponseDto;
import io.github.wolfandw.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostToDtoMapperImpl implements PostToDtoMapper {
    private static final int MAX_TEXT_LENGTH = 128;

    public PostResponseDto mapPostToPostResponseDto(Post post) {
        String text = post.getText();
        if (text.length() >= MAX_TEXT_LENGTH) {
            text = text.substring(0, MAX_TEXT_LENGTH) + "...";
        }
        text += System.lineSeparator() + post.getImage();
        return new PostResponseDto(post.getId(),
                post.getTitle(),
                text,
                post.getTags(),
                post.getLikesCount(),
                post.getCommentsCount());
    }
}
