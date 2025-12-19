package io.github.wolfandw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс модели страницы постов.
 */
public class PostsPage {
    private List<Post> posts = new ArrayList<>();
    private final boolean hasPrev;
    private final boolean hasNext;
    private final int lastPage;

    /**
     * Создает страницу постов.
     *
     * @param posts    список постов
     * @param hasPrev  есть предыдущая страница
     * @param hasNext  есть следующая страница
     * @param lastPage номер последней страницы
     */
    public PostsPage(List<Post> posts,
                     boolean hasPrev,
                     boolean hasNext,
                     int lastPage) {
        this.posts = posts;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.lastPage = lastPage;
    }

    /**
     * Возвращает список постов.
     *
     * @return список постов
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Есть предыдущая страница.
     *
     * @return есть предыдущая страница
     */
    public boolean hasPrev() {
        return hasPrev;
    }

    /**
     * Есть следующая страница.
     *
     * @return есть следующая страница
     */
    public boolean hasNext() {
        return hasNext;
    }

    /**
     * Возвращает номер последней страницы.
     *
     * @return номер последней страницы
     */
    public int getLastPage() {
        return lastPage;
    }
}
