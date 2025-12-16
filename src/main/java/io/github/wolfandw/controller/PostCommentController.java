package io.github.wolfandw.controller;

import io.github.wolfandw.controller.mapper.PostCommentToDtoMapper;
import io.github.wolfandw.dto.PostCommentCreateRequestDto;
import io.github.wolfandw.dto.PostCommentResponseDto;
import io.github.wolfandw.dto.PostCommentUpdateRequestDto;
import io.github.wolfandw.service.PostCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Реазизация контроллера комментариев постов.
 */
@RestController
@RequestMapping("/api/posts")
public class PostCommentController {
    private final PostCommentService postCommentService;
    private final PostCommentToDtoMapper postCommentToDtoMapper;

    /**
     * Создает контроллер комментариев постов.
     *
     * @param postCommentService     сервис комментариев постов
     * @param postCommentToDtoMapper маппер комментария поста в DTO комментария поста
     */
    public PostCommentController(PostCommentService postCommentService, PostCommentToDtoMapper postCommentToDtoMapper) {
        this.postCommentService = postCommentService;
        this.postCommentToDtoMapper = postCommentToDtoMapper;
    }

    /**
     * Возвращает список комментариев поста.
     *
     * @param postId идентификатор поста
     * @return список DTO комментариев поста
     */
    @GetMapping("/{postId}/comments")
    public List<PostCommentResponseDto> getPostComments(@PathVariable("postId") Long postId) {
        return postCommentService.getPostComments(postId).stream().map(postCommentToDtoMapper::mapPostCommentToPostCommentResponseDto).toList();
    }

    /**
     * Возвращает комментарий поста.
     *
     * @param postId    идентификатор поста
     * @param commentId идентификатор комментария поста
     * @return DTO комментария поста
     */
    @GetMapping("/{postId}/comments/{commentId}")
    public PostCommentResponseDto getPostComment(@PathVariable("postId") Long postId,
                                                 @PathVariable("commentId") Long commentId) {
        return postCommentService.getPostComment(postId, commentId).map(postCommentToDtoMapper::mapPostCommentToPostCommentResponseDto).orElse(null);
    }

    /**
     * Создает комментарий поста.
     *
     * @param postId            идентификатор поста
     * @param commentRequestDto DTO запроса на создание комментария поста
     * @return DTO созданного комментария поста
     */
    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCommentResponseDto createPostComment(@PathVariable("postId") Long postId,
                                                    @RequestBody PostCommentCreateRequestDto commentRequestDto) {
        return postCommentService.createPostComment(postId, commentRequestDto.text()).map(postCommentToDtoMapper::mapPostCommentToPostCommentResponseDto).orElse(null);
    }

    /**
     * Обновляет комментарий поста.
     *
     * @param postId            идентификатор поста
     * @param commentId         идентификатор комментария поста
     * @param commentRequestDto DTO запроса на обновления комментария поста
     * @return DTO обновленного комментария поста
     */
    @PutMapping("/{postId}/comments/{commentId}")
    public PostCommentResponseDto updatePostComment(@PathVariable("postId") Long postId,
                                                    @PathVariable("commentId") Long commentId,
                                                    @RequestBody PostCommentUpdateRequestDto commentRequestDto) {
        return postCommentService.updatePostComment(postId, commentId, commentRequestDto.text()).map(postCommentToDtoMapper::mapPostCommentToPostCommentResponseDto).orElse(null);
    }

    /**
     * Удаляет комментарий поста.
     *
     * @param postId    идентификатор поста
     * @param commentId идентификатор комментария поста
     */
    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePostComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        postCommentService.deletePostComment(postId, commentId);
    }
}
