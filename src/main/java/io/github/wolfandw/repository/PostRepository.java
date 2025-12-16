package io.github.wolfandw.repository;

import io.github.wolfandw.model.Post;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с постами.
 */
public interface PostRepository {
    /**
     * Возвращает список постов.
     *
     * @param searchWords список ключевых слов для поиска в заголовках постов
     * @param tags        список тэгов для поиска в тэгах постов
     * @param pageNumber  номер страницы постов
     * @param pageSize    размер страницы постов
     * @return список постов
     */
    List<Post> getPosts(List<String> searchWords, List<String> tags, int pageNumber, int pageSize);

    /**
     * Возвращает пост.
     *
     * @param postId идентификатор поста
     * @return пост
     */
    Optional<Post> getPost(Long postId);

    /**
     * Создает и возвращает пост.
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
     * @return новое количество лайков
     */
    int increasePostLikesCount(Long postId);

    /**
     * Увеличивает количество комментариев поста на единицу.
     *
     * @param postId идентификатор поста
     */
    void increasePostCommentCount(Long postId);

    /**
     * Уменьшает количество комментариев поста на единицу.
     *
     * @param postId идентификатор поста
     */
    void decreasePostCommentCount(Long postId);

    /**
     * Возвращает общее количество постов.
     *
     * @param searchWords список ключевых слов для поиска в заголовках постов
     * @param tags        список тэгов для поиска в тэгах постов
     * @return общее количество постов
     */
    int getPostsCount(List<String> searchWords, List<String> tags);

    /**
     * Обновляет имя файла картинки.
     *
     * @param postId    идентификатор поста
     * @param imageName имя файла картинки
     */
    void updatePostImageName(Long postId, String imageName);

    /**
     * Возвращает имя файла картинки поста.
     *
     * @param postId идентификатор поста
     * @return имя файла картинки
     */
    Optional<String> getPostImageName(Long postId);
}

