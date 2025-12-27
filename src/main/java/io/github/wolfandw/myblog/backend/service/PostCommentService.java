package io.github.wolfandw.myblog.backend.service;

import io.github.wolfandw.myblog.backend.model.PostComment;

import java.util.List;
import java.util.Optional;

/**
 * Сервис работы с комментариями постов.
 */
public interface PostCommentService {
    /**
     * Возвращает список комментариев поста.
     *
     * @param postId идентификатор поста.
     * @return список комментариев поста
     */
    List<PostComment> getPostComments(Long postId);

    /**
     * Возвращает комментарий поста.
     *
     * @param postId    идентификатор поств
     * @param commentId идентификатор комментария поста
     * @return комментарий поста
     */
    Optional<PostComment> getPostComment(Long postId, Long commentId);

    /**
     * Создает и возвращает комментарий поста.
     *
     * @param postId идентификатор поста
     * @param text   текст комментария поста
     * @return созданный пост
     */
    Optional<PostComment> createPostComment(Long postId, String text);

    /**
     * Обновляет комментарий поста.
     *
     * @param postId    идентификатор поста
     * @param commentId идентификатор комментария поста
     * @param text      текст комментария поста
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
}
