package io.github.wolfandw.myblog.backend.itest.repository;

import io.github.wolfandw.myblog.backend.itest.AbstractPostIntegrationTest;
import io.github.wolfandw.myblog.backend.model.PostComment;
import io.github.wolfandw.myblog.backend.repository.PostCommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционный тест репозитория {@link PostCommentRepository}
 */
public class PostCommentRepositoryIntegrationTest extends AbstractPostIntegrationTest {
    @Autowired
    private PostCommentRepository postCommentRepository;

    @Test
    void getPostCommentsTest() {
        List<PostComment> comments = postCommentRepository.getPostComments(1L);

        assertNotNull(comments, "Спикок комментариев постов не должен быть нулл");
        assertEquals(3, comments.size(), "Размер тестового списка комментариев должен быть равен указанному");

        assertEquals(1L, comments.getFirst().getId(), "Идентификатор 1 комментария должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 1 comment 1", comments.getFirst().getText(), "Имя 1 комментария должно совпадать");
        assertEquals(1L, comments.getFirst().getPostId(), "Идентификатор поста 1 комментария лайков должно совпадать");

        assertEquals(2L, comments.get(1).getId(), "Идентификатор 2 комментария должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 1 comment 2", comments.get(1).getText(), "Имя 2 комментария должно совпадать");
        assertEquals(1L, comments.get(1).getPostId(), "Идентификатор поста 2 комментария лайков должно совпадать");

        assertEquals(3L, comments.getLast().getId(), "Идентификатор 3 комментария должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 1 comment 3", comments.getLast().getText(), "Имя 3 комментария должно совпадать");
        assertEquals(1L, comments.getLast().getPostId(), "Идентификатор поста 3 комментария лайков должно совпадать");
    }

    @ParameterizedTest
    @ValueSource(longs = {14L, 0L, -1L})
    void getPostCommentsEmptyTest(Long postId) {
        List<PostComment> comments = postCommentRepository.getPostComments(postId);

        assertNotNull(comments, "Спикок комментариев постов не должен быть нулл");
        assertEquals(0, comments.size(), "Размер тестового списка комментариев должен быть равен указанному");
    }

    @Test
    void getPostCommentTest() {
        Optional<PostComment> comment = postCommentRepository.getPostComment(1L, 1L);

        assertTrue(comment.isPresent(), "Комментарий не должен быть пустым");
        PostComment postCommentInstance = comment.get();

        assertEquals(1L, postCommentInstance.getId(), "Идентификатор 1 комментария должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 1 comment 1", postCommentInstance.getText(), "Имя 1 комментария должно совпадать");
        assertEquals(1L, postCommentInstance.getPostId(), "Идентификатор поста 1 комментария лайков должно совпадать");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 4",
            "1, 0",
            "1, -1"
    })
    void getPostCommentEmptyTest(Long postId, Long commentId) {
        Optional<PostComment> comment = postCommentRepository.getPostComment(postId, commentId);

        assertTrue(comment.isEmpty(), "Комментарий должен быть пустым");
    }

    @Test
    void createPostCommentTest() {
        Optional<PostComment> comment = postCommentRepository.createPostComment(1L, "Post 1 comment 4");

        assertTrue(comment.isPresent(), "Комментарий не должен быть пустым");
        PostComment postCommentInstance = comment.get();

        assertEquals(40L, postCommentInstance.getId(), "Идентификатор 4 комментария должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 1 comment 4", postCommentInstance.getText(), "Имя 4 комментария должно совпадать");
        assertEquals(1L, postCommentInstance.getPostId(), "Идентификатор поста 4 комментария лайков должно совпадать");
    }

    @Test
    void updatePostCommentTest() {
        Optional<PostComment> commentBefore = postCommentRepository.getPostComment(1L, 3L);
        assertTrue(commentBefore.isPresent(), "Комментарий до обновления не должен быть пустым");

        Optional<PostComment> comment = postCommentRepository.updatePostComment(1L, 3L, "Post 1 comment 3 updated");
        assertTrue(comment.isPresent(), "Комментарий после обновления не должен быть пустым");
        assertEquals(commentBefore.get().getId(), comment.get().getId(), "Идентификатор 3 комментария должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 1 comment 3 updated", comment.get().getText(), "Имя 3 комментария должно совпадать");
        assertEquals(commentBefore.get().getPostId(), comment.get().getPostId(), "Идентификатор поста 3 комментария лайков должно совпадать");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 4",
            "1, 0",
            "1, -1"
    })
    void updatePostCommentEmptyTest(Long postId, Long commentId) {
        Optional<PostComment> comment = postCommentRepository.updatePostComment(postId, commentId, "");

        assertTrue(comment.isEmpty(), "Комментарий должен быть пустым");
    }

    @Test
    void deletePostCommentTest() {
        Optional<PostComment> commentBefore = postCommentRepository.getPostComment(1L, 3L);
        assertTrue(commentBefore.isPresent(), "Комментарий до удаления не должен быть пустым");

        postCommentRepository.deletePostComment(1L, 3L);

        Optional<PostComment> comment = postCommentRepository.getPostComment(1L, 3L);
        assertTrue(comment.isEmpty(), "Комментарий после удаления должен быть пустым");
    }

    @Test
    void deletePostCommentsTest() {
        List<PostComment> commentsBefore = postCommentRepository.getPostComments(1L);

        assertNotNull(commentsBefore, "Спикок комментариев постов до удаления не должен быть нулл");
        assertEquals(3, commentsBefore.size(), "Размер тестового списка комментариев до удаления должен быть равен указанному");

        postCommentRepository.deletePostComments(1L);

        List<PostComment> comments = postCommentRepository.getPostComments(1L);

        assertNotNull(comments, "Спикок комментариев постов после удаления не должен быть нулл");
        assertEquals(0, comments.size(), "Размер тестового списка комментариев после удаления должен быть равен указанному");
    }
}
