package io.github.wolfandw.myblog.backend.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс модели поста.
 */
public class Post {
    private final Long id;
    private String title;
    private String text;
    private List<String> tags = new ArrayList<>();
    private int likesCount;
    private int commentsCount;
    private String imageName;

    /**
     * Создает пост.
     *
     * @param id            идентификатор поста
     * @param title         заголовок поста
     * @param text          текст поста
     * @param tags          список тэгов поста
     * @param likesCount    колчество лайков поста
     * @param commentsCount количество комментариев поста
     * @param imageName     имя файла картинки поста
     */
    public Post(Long id,
                String title,
                String text,
                List<String> tags,
                int likesCount,
                int commentsCount,
                String imageName) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.tags = tags;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.imageName = imageName;
    }

    /**
     * Возвращает идентификатор поста.
     *
     * @return идентификатор поста
     */
    public Long getId() {
        return id;
    }

    /**
     * Возвращает текст поста.
     *
     * @return текст поста
     */
    public String getText() {
        return text;
    }

    /**
     * Устанавливает текст поста.
     *
     * @param text текст поста
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Возвращает заголовок поста.
     *
     * @return заголовок поста
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает заголовок поста.
     *
     * @param title заголовок поста
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Возвращает список тэгов поста.
     *
     * @return список тэгов поста
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Устанавливает список тэгов поста.
     *
     * @param tags список тэгов поста
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Возвращает количество лайков поста.
     *
     * @return количество лайков поста
     */
    public int getLikesCount() {
        return likesCount;
    }

    /**
     * Устанавливает количество лайков поста.
     *
     * @param likesCount количество лайков поста
     */
    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    /**
     * Возвращает количество комментариев поста.
     *
     * @return количество комментариев поста
     */
    public int getCommentsCount() {
        return commentsCount;
    }

    /**
     * Устанавливает количество комментариев поста.
     *
     * @param commentsCount количество комментариев поста
     */
    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     * Возвращает имя файла картинки поста.
     *
     * @return имя файла картинки поста
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Устанавливает имя файла картинки поста.
     *
     * @param imageName имя файла картинки поста
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}