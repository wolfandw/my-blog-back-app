package io.github.wolfandw.model;

/**
 * Класс модели тэга поста.
 */
public class PostTag {
    private final Long id;
    private final Long postId;
    private final String name;

    /**
     * Создает тэг поста.
     *
     * @param id     идентификатор тэга поста
     * @param postId идентификатор поста
     * @param name   имя тэга
     */
    public PostTag(Long id, Long postId, String name) {
        this.id = id;
        this.postId = postId;
        this.name = name;
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
     * Возвращает имя тэга.
     *
     * @return имя тэга
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает идентификатор тэга поста.
     *
     * @return идентификатор тэга поста
     */
    public Long getId() {
        return id;
    }
}
