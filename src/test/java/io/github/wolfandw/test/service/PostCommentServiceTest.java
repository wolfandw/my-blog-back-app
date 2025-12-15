package io.github.wolfandw.test.service;

import io.github.wolfandw.model.PostComment;
import io.github.wolfandw.repository.PostCommentRepository;
import io.github.wolfandw.repository.PostRepository;
import io.github.wolfandw.service.PostCommentService;
import io.github.wolfandw.test.AbstractPostTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostCommentServiceTest extends AbstractPostTest {
    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    private Map<Long, List<PostComment>> comments = new HashMap<>();

    @BeforeEach
    void setUp() {
        reset(postRepository);
        reset(postCommentRepository);

        comments = LongStream.range(1, 16).boxed()
                .collect(Collectors.toMap(Function.identity(), postId -> LongStream.range(1, 4)
                        .boxed().map(commentId ->
                                new PostComment(commentId,
                                        "Test Post " + postId + ", comment " + commentId,
                                        postId)).toList()));

    }

    @Test
    void testGetPostComments() {
        Long postId = 5L;
        List<PostComment> mockPostComments = comments.get(postId);
        when(postCommentRepository.getPostComments(postId)).thenReturn(mockPostComments);

        List<PostComment> postComments = postCommentService.getPostComments(postId);
        assertArrayEquals(mockPostComments.toArray(), postComments.toArray(),
                "Список комментариев постов должен соответствовать исходному");
    }

    @Test
    void testGetPostComment() {
        Long postId = 5L;
        Long commentId = 3L;

        PostComment mockPostComment = comments.get(postId).getLast();
        when(postCommentRepository.getPostComment(postId, commentId)).thenReturn(Optional.of(mockPostComment));

        Optional<PostComment> postComment = postCommentService.getPostComment(postId, commentId);

        assertTrue(postComment.isPresent(), "Комментарий поста должен присутствовать");
        PostComment postCommentInstance = postComment.get();
        assertEquals(mockPostComment.getId(), postCommentInstance.getId(), "Идентификатор комментария должен совпадать с исходным");
        assertEquals(mockPostComment.getText(), postCommentInstance.getText(), "Текст комментария должен совпадать с исходным");
        assertEquals(mockPostComment.getPostId(), postCommentInstance.getPostId());
    }

    @Test
    void testCreatePostComment() {
        Long postId = 5L;
        Long commentId = 4L;
        String text = "Test Post " + postId + ", comment " + commentId;

        PostComment mockPostComment = new PostComment(commentId, text, postId);
        when(postCommentRepository.createPostComment(postId, text)).thenReturn(Optional.of(mockPostComment));
        doNothing().when(postRepository).increasePostCommentCount(postId);

        Optional<PostComment> postComment = postCommentService.createPostComment(postId, text);

        assertTrue(postComment.isPresent(), "Комментарий поста должен быть создан и получен");
        PostComment postCommentInstance = postComment.get();
        assertEquals(mockPostComment.getId(), postCommentInstance.getId(), "Идентификатор комментария должен совпадать с исходным");
        assertEquals(mockPostComment.getText(), postCommentInstance.getText(), "Текст комментария должен совпадать с исходным");
        assertEquals(mockPostComment.getPostId(), postCommentInstance.getPostId());
        verify(postRepository).increasePostCommentCount(postId);
    }

    @Test
    void testUpdatePostComment() {
        Long postId = 5L;
        Long commentId = 3L;
        String text = "Test Post " + postId + ", comment update" + commentId;
        PostComment postCommentBeforeUpdate = comments.get(postId).getLast();

        PostComment mockPostComment = new PostComment(postCommentBeforeUpdate.getId(),
                text,
                postCommentBeforeUpdate.getPostId());
        when(postCommentRepository.updatePostComment(postId, commentId, text)).thenReturn(Optional.of(mockPostComment));

        Optional<PostComment> postComment = postCommentService.updatePostComment(postId, commentId, text);

        assertTrue(postComment.isPresent(), "Комментарий поста должен быть обновлен и получен");
        PostComment postCommentInstance = postComment.get();
        assertEquals(mockPostComment.getId(), postCommentInstance.getId(), "Идентификатор комментария должен совпадать с исходным");
        assertEquals(mockPostComment.getText(), postCommentInstance.getText(), "Текст комментария должен совпадать с исходным");
        assertEquals(mockPostComment.getPostId(), postCommentInstance.getPostId());
    }

    @Test
    void testDeletePost() {
        Long postId = 5L;
        Long commentId = 3L;
        doNothing().when(postCommentRepository).deletePostComment(postId, commentId);
        doNothing().when(postRepository).decreasePostCommentCount(postId);

        postCommentService.deletePostComment(postId, commentId);

        verify(postCommentRepository).deletePostComment(postId, commentId);
        verify(postRepository).decreasePostCommentCount(postId);
    }
}
