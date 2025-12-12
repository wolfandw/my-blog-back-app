package io.github.wolfandw.controller;

import io.github.wolfandw.controller.mapper.PostCommentToDtoMapper;
import io.github.wolfandw.dto.PostCommentCreateRequestDto;
import io.github.wolfandw.dto.PostCommentResponseDto;
import io.github.wolfandw.dto.PostCommentUpdateRequestDto;
import io.github.wolfandw.service.PostCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostCommentController {
    private final PostCommentService postCommentService;
    private final PostCommentToDtoMapper postCommentToDtoMapper;

    public PostCommentController(PostCommentService postCommentService, PostCommentToDtoMapper postCommentToDtoMapper) {
        this.postCommentService = postCommentService;
        this.postCommentToDtoMapper = postCommentToDtoMapper;
    }

    @GetMapping("/{postId}/comments")
    public List<PostCommentResponseDto> getPostComments(@PathVariable("postId") Long postId) {
        return postCommentService.getPostComments(postId).stream().map(postCommentToDtoMapper::mapPostCommentToPostCommentResponseDto).toList();
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public PostCommentResponseDto getPostComment(@PathVariable("postId") Long postId,
                                                 @PathVariable("commentId") Long commentId) {
        return postCommentService.getPostComment(postId, commentId).map(postCommentToDtoMapper::mapPostCommentToPostCommentResponseDto).orElse(null);
    }

    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCommentResponseDto createPostComment(@PathVariable("postId") Long postId,
                                                    @RequestBody PostCommentCreateRequestDto commentRequest) {
        return postCommentService.createPostComment(postId, commentRequest.text()).map(postCommentToDtoMapper::mapPostCommentToPostCommentResponseDto).orElse(null);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public PostCommentResponseDto updatePostComment(@PathVariable("postId") Long postId,
                                                    @PathVariable("commentId") Long commentId,
                                                    @RequestBody PostCommentUpdateRequestDto commentRequest) {
        return postCommentService.updatePostComment(postId, commentId, commentRequest.text()).map(postCommentToDtoMapper::mapPostCommentToPostCommentResponseDto).orElse(null);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePostComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        postCommentService.deletePostComment(postId, commentId);
    }
}
