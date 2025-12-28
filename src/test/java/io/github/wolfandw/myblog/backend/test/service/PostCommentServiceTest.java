package io.github.wolfandw.myblog.backend.test.service;

import io.github.wolfandw.myblog.backend.model.PostComment;
import io.github.wolfandw.myblog.backend.repository.PostCommentRepository;
import io.github.wolfandw.myblog.backend.repository.PostRepository;
import io.github.wolfandw.myblog.backend.service.PostCommentService;
import io.github.wolfandw.myblog.backend.test.AbstractPostTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Модульный тест комментариев постов.
 */
public class PostCommentServiceTest extends AbstractPostTest {
    @Autowired
    @Qualifier("postCommentServiceTest")
    private PostCommentService postCommentServiceTest;

    @Autowired
    @Qualifier("postRepositoryTest")
    private PostRepository postRepositoryTest;

    @Autowired
    @Qualifier("postCommentRepositoryTest")
    private PostCommentRepository postCommentRepositoryTest;

    private Map<Long, List<PostComment>> comments = new HashMap<>();

    @BeforeEach
    void setUp() {
        reset(postRepositoryTest);
        reset(postCommentRepositoryTest);

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
        when(postCommentRepositoryTest.getPostComments(postId)).thenReturn(mockPostComments);

        List<PostComment> postComments = postCommentServiceTest.getPostComments(postId);
        assertArrayEquals(mockPostComments.toArray(), postComments.toArray(),
                "Список комментариев постов должен соответствовать исходному");
    }

    @Test
    void testGetPostComment() {
        Long postId = 5L;
        Long commentId = 3L;

        PostComment mockPostComment = comments.get(postId).getLast();
        when(postCommentRepositoryTest.getPostComment(postId, commentId)).thenReturn(Optional.of(mockPostComment));

        Optional<PostComment> postComment = postCommentServiceTest.getPostComment(postId, commentId);

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
        when(postCommentRepositoryTest.createPostComment(postId, text)).thenReturn(Optional.of(mockPostComment));
        doNothing().when(postRepositoryTest).increasePostCommentCount(postId);

        Optional<PostComment> postComment = postCommentServiceTest.createPostComment(postId, text);

        assertTrue(postComment.isPresent(), "Комментарий поста должен быть создан и получен");
        PostComment postCommentInstance = postComment.get();
        assertEquals(mockPostComment.getId(), postCommentInstance.getId(), "Идентификатор комментария должен совпадать с исходным");
        assertEquals(mockPostComment.getText(), postCommentInstance.getText(), "Текст комментария должен совпадать с исходным");
        assertEquals(mockPostComment.getPostId(), postCommentInstance.getPostId());
        verify(postRepositoryTest).increasePostCommentCount(postId);
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
        when(postCommentRepositoryTest.updatePostComment(postId, commentId, text)).thenReturn(Optional.of(mockPostComment));

        Optional<PostComment> postComment = postCommentServiceTest.updatePostComment(postId, commentId, text);

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
        doNothing().when(postCommentRepositoryTest).deletePostComment(postId, commentId);
        doNothing().when(postRepositoryTest).decreasePostCommentCount(postId);

        postCommentServiceTest.deletePostComment(postId, commentId);

        verify(postCommentRepositoryTest).deletePostComment(postId, commentId);
        verify(postRepositoryTest).decreasePostCommentCount(postId);
    }
}
