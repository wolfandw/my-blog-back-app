package io.github.wolfandw.service;

import io.github.wolfandw.model.Post;
import io.github.wolfandw.model.PostsPage;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с постами.
 */
public interface PostService {
    /**
     * Возвращает страницу постов.
     *
     * @param search     строка поиска
     * @param pageNumber номер страницы постов
     * @param pageSize   размер страницы постов
     * @return страница постов
     */
    PostsPage getPostsPage(String search, int pageNumber, int pageSize);

    /**
     * Возвращает пост.
     *
     * @param postId идентификатор поста
     * @return пост
     */
    Optional<Post> getPost(Long postId);

    /**
     * Создает пост и возвращает пост.
     *
     * @param title заголовок поста
     * @param text  текст поста
     * @param tags  тэги поста
     * @return созданный пост
     */
    Optional<Post> createPost(String title, String text, List<String> tags);

    /**
     * Обновляет и возвращает пост.
     *
     * @param postId идентификатор поста
     * @param title  заголовок поста
     * @param text   текст поста
     * @param tags   тэги поста
     * @return обновленный пост
     */
    Optional<Post> updatePost(Long postId, String title, String text, List<String> tags);

    /**
     * Удаляет пост.
     *
     * @param postId идентификатор поста
     */
    void deletePost(Long postId);

    /**
     * Увеличивает количество лайков поста на единицу.
     *
     * @param postId идентификатор поста
     * @return новое количество лайков поста
     */
    int increasePostLikesCount(Long postId);
}
