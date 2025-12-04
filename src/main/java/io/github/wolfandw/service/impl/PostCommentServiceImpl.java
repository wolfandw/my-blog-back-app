package io.github.wolfandw.service.impl;

import io.github.wolfandw.dto.PostCommentCreateRequestDto;
import io.github.wolfandw.dto.PostCommentResponseDto;
import io.github.wolfandw.dto.PostCommentUpdateRequestDto;
import io.github.wolfandw.service.PostCommentService;
import io.github.wolfandw.service.PostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    public static final Map<Long, List<PostCommentResponseDto>> POST_COMMENT_REPOSITORY = new HashMap<>();
    private static Long maxId = 1L;

    private final PostService postService;

    public PostCommentServiceImpl(PostService postService) {
        this.postService = postService;

        IntStream.range(1, 4).forEach(i -> {
            Long postId = (long) i;
            List<PostCommentResponseDto> postCommentResponseDtoList = POST_COMMENT_REPOSITORY.computeIfAbsent(postId, v -> new ArrayList<>());
            for (int j = 0; j < i; j++) {
                postCommentResponseDtoList.add(createComment(postId, new PostCommentCreateRequestDto(
                        "Post " + i + ", comment " + (maxId + 1),
                        postId)));
            }
        });
    }

    @Override
    public List<PostCommentResponseDto> getComments(Long postId) {
        return POST_COMMENT_REPOSITORY.computeIfAbsent(postId, v -> new ArrayList<>());
    }

    @Override
    public PostCommentResponseDto getComment(Long postId, Long commentId) {
        List<PostCommentResponseDto> postCommentResponseDtoList = POST_COMMENT_REPOSITORY.computeIfAbsent(postId, v -> new ArrayList<>());
        for (PostCommentResponseDto postCommentResponseDto : postCommentResponseDtoList) {
            if (postCommentResponseDto.id().equals(commentId)) {
                return postCommentResponseDto;
            }
        }
        return null;
    }

    @Override
    public PostCommentResponseDto createComment(Long postId,
                                                PostCommentCreateRequestDto commentRequest) {
        PostCommentResponseDto postCommentResponseDto = new PostCommentResponseDto(maxId,
                commentRequest.text(),
                commentRequest.postId());
        maxId++;
        postService.increaseCommentCount(postId);
        return postCommentResponseDto;
    }

    @Override
    public PostCommentResponseDto updateComment(Long postId, Long commentId,
                                                PostCommentUpdateRequestDto commentRequest) {
        PostCommentResponseDto source = getComment(postId, commentId);
        if (source != null) {
            List<PostCommentResponseDto> postCommentResponseDtoList = POST_COMMENT_REPOSITORY.computeIfAbsent(postId, v -> new ArrayList<>());
            PostCommentResponseDto target = new PostCommentResponseDto(
                    source.id(),
                    commentRequest.text(),
                    commentRequest.postId());
            postCommentResponseDtoList.set(target.id().intValue() - 1, target);
            return target;
        }
        return null;
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        List<PostCommentResponseDto> postCommentResponseDtoList = POST_COMMENT_REPOSITORY.computeIfAbsent(postId, v -> new ArrayList<>());
        postCommentResponseDtoList.removeIf(comment -> comment.id().equals(commentId));
        postService.decreaseCommentCount(postId);
    }
}
