package io.github.wolfandw.model;

/**
 * Класс модели комментария поста.
 */
public class PostComment {
    private final Long id;
    private String text;
    private final Long postId;

    /**
     * Создание комментария поста.
     *
     * @param id     идентификатор комментария поста
     * @param text   текст комментария поста
     * @param postId идентификатор поста
     */
    public PostComment(Long id, String text, Long postId) {
        this.id = id;
        this.text = text;
        this.postId = postId;
    }

    /**
     * Возвращает идентификатор комментария поста.
     *
     * @return идентификатор комментария поста
     */
    public Long getId() {
        return id;
    }

    /**
     * Возвращает идентификатор поста.
     *
     * @return идентификатор поста
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * Возвращает текст комментария поста.
     *
     * @return текст комментария поста
     */
    public String getText() {
        return text;
    }

    /**
     * Устанавливает текст комментария поста.
     *
     * @param text текст комментария поста
     */
    public void setText(String text) {
        this.text = text;
    }
}
