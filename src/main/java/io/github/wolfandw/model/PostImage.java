package io.github.wolfandw.model;

import org.springframework.http.MediaType;

/**
 * Класс модели картинки поста.
 */
public class PostImage {
    private byte[] data;
    private MediaType mediaType;
    private final Long postId;

    /**
     * Создает картинку поста.
     *
     * @param data      данные картинки поста
     * @param mediaType тип данных картинки поста
     * @param postId    идентификатор поста
     */
    public PostImage(byte[] data, MediaType mediaType, Long postId) {
        this.data = data;
        this.mediaType = mediaType;
        this.postId = postId;
    }

    /**
     * Устанавливает данные картинки поста.
     *
     * @param data данные картинки поста
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Возвращает данные картинки поста.
     *
     * @return данные картинки поста
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Устанавливает тип данных картинки поста.
     *
     * @param mediaType тип данных картинки поста
     */
    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * Возвращает тип данных картинки поста.
     *
     * @return тип данных картинки поста
     */
    public MediaType getMediaType() {
        return mediaType;
    }

    /**
     * Возвращает идентификатор поста.
     *
     * @return идентификатор поста
     */
    public Long getPostId() {
        return postId;
    }
}
