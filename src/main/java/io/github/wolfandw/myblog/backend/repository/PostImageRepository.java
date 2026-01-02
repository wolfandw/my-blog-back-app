package io.github.wolfandw.myblog.backend.repository;

import java.util.Optional;

/**
 * Ревозиторий для работы с картинками постов.
 */
public interface PostImageRepository {
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
