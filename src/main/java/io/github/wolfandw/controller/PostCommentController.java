package io.github.wolfandw.controller;

import io.github.wolfandw.dto.PostCommentCreateRequestDto;
import io.github.wolfandw.dto.PostCommentResponseDto;
import io.github.wolfandw.dto.PostCommentUpdateRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostCommentController {
    @GetMapping("/{postId}/comments")
    public List<PostCommentResponseDto> getComments(@PathVariable("postId") Long postId) {
        return List.of(new PostCommentResponseDto(1L, "First comment", postId));
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public PostCommentResponseDto getComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId) {
        return new PostCommentResponseDto(commentId, "First comment", postId);
    }

    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCommentResponseDto createComment(
            @PathVariable("postId") Long postId,
            @RequestBody PostCommentCreateRequestDto commentRequest) {
        return new PostCommentResponseDto(99L, "First comment", postId);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public PostCommentResponseDto updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody PostCommentUpdateRequestDto commentRequest) {
        return new PostCommentResponseDto(99L, "First comment", postId);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId) {
    }
}
