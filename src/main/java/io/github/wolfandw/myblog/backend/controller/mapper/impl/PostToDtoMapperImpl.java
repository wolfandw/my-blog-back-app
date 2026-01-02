package io.github.wolfandw.myblog.backend.controller.mapper.impl;

import io.github.wolfandw.myblog.backend.controller.mapper.PostToDtoMapper;
import io.github.wolfandw.myblog.backend.dto.PostResponseDto;
import io.github.wolfandw.myblog.backend.model.Post;
import org.springframework.stereotype.Component;

/**
 * Реализация {@link PostToDtoMapper}.
 */
@Component
public class PostToDtoMapperImpl implements PostToDtoMapper {
    private static final int MAX_TEXT_LENGTH = 128;

    @Override
    public PostResponseDto mapPostToPostResponseDto(Post post) {
        String text = post.getText();
        if (text.length() >= MAX_TEXT_LENGTH) {
            text = text.substring(0, MAX_TEXT_LENGTH) + "...";
        }
        return new PostResponseDto(post.getId(),
                post.getTitle(),
                text,
                post.getTags(),
                post.getLikesCount(),
                post.getCommentsCount());
    }
}
