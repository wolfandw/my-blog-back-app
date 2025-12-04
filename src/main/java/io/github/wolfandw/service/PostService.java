package io.github.wolfandw.service;

import io.github.wolfandw.dto.PostCreateRequestDto;
import io.github.wolfandw.dto.PostListResponseDto;
import io.github.wolfandw.dto.PostResponseDto;
import io.github.wolfandw.dto.PostUpdateRequestDto;

public interface PostService {
    PostListResponseDto getPosts(
            String search,
            int pageNumber,
            int pageSize);

    PostResponseDto getPost(Long id);

    PostResponseDto createPost(PostCreateRequestDto postRequest);

    PostResponseDto updatePost(
            Long id,
            PostUpdateRequestDto postRequest);

    void deletePost(Long id);

    int increaseLikesCount(Long id);

    void increaseCommentCount(Long id);

    void decreaseCommentCount(Long id);
}
