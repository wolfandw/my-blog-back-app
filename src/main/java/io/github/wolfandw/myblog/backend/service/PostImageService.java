package io.github.wolfandw.myblog.backend.service;

import io.github.wolfandw.myblog.backend.model.PostImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для работы с картинками поста.
 */
public interface PostImageService {
    /**
     * Возвращает картинку поста.
     *
     * @param postId идентификатор поста
     * @return картинка поста
     */
    PostImage getPostImage(Long postId);

    /**
     * Обновляет картинку поста.
     *
     * @param postId идентификатор поста
     * @param image  файл картинки поста
     */
    void updatePostImage(Long postId, MultipartFile image);

    /**
     * Удаляет картинку поста.
     *
     * @param postId идентификатор поста
     */
    void deletePostImage(Long postId);
}
