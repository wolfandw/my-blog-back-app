package io.github.wolfandw.repository;

import io.github.wolfandw.model.PostComment;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с комментариями постов.
 */
public interface PostCommentRepository {
    /**
     * Возвращает список постов.
     *
     * @param postId идентификатор поста
     * @return список постов
     */
    List<PostComment> getPostComments(Long postId);

    /**
     * Возвращает комментарий поста.
     *
     * @param postId    идентификатор поста
     * @param commentId идентификатор комментария поста
     * @return комментарий поста
     */
    Optional<PostComment> getPostComment(Long postId, Long commentId);

    /**
     * Возвращает созданный комментарий поста.
     *
     * @param postId идентификатор поста
     * @param text   текст поста
     * @return созданный комментарий поста
     */
    Optional<PostComment> createPostComment(Long postId, String text);

    /**
     * Возвращает обновленный комментарий поста.
     *
     * @param postId    идентификатор поста
     * @param commentId идентификатор комментария поста
     * @param text      текст поста
     * @return обновленный комментарий поста
     */
    Optional<PostComment> updatePostComment(Long postId, Long commentId, String text);

    /**
     * Удаляет комментарий поста.
     *
     * @param postId    идентификатор поста
     * @param commentId идентификатор комментария поста
     */
    void deletePostComment(Long postId, Long commentId);

    /**
     * Удаляет комментарии поста.
     *
     * @param postId идентификатор поста
     */
    void deletePostComments(Long postId);
}
