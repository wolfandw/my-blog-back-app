package io.github.wolfandw.controller;

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

    public PostCommentController(PostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @GetMapping("/{postId}/comments")
    public List<PostCommentResponseDto> getComments(@PathVariable("postId") Long postId) {
        return postCommentService.getComments(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public PostCommentResponseDto getComment(@PathVariable("postId") Long postId,
                                             @PathVariable("commentId") Long commentId) {
        return postCommentService.getComment(postId, commentId);
    }

    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCommentResponseDto createComment(@PathVariable("postId") Long postId,
                                                @RequestBody PostCommentCreateRequestDto commentRequest) {
        return postCommentService.createComment(postId, commentRequest);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public PostCommentResponseDto updateComment(@PathVariable("postId") Long postId,
                                                @PathVariable("commentId") Long commentId,
                                                @RequestBody PostCommentUpdateRequestDto commentRequest) {
        return postCommentService.updateComment(postId, commentId, commentRequest);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        postCommentService.deleteComment(postId, commentId);
    }
}
