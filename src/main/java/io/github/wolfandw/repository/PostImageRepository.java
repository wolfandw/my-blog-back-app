package io.github.wolfandw.repository;

import io.github.wolfandw.model.PostImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * Ревозиторий для работы с картинками постов.
 */
public interface PostImageRepository {
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
