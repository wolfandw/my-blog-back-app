package io.github.wolfandw.model;

import java.util.ArrayList;
import java.util.List;

public class PostsPage {
    private List<Post> posts = new ArrayList<>();
    private final boolean hasPrev;
    private final boolean hasNext;
    private final int lastPage;

    public PostsPage(List<Post> posts,
                     boolean hasPrev,
                     boolean hasNext,
                     int lastPage) {
        this.posts = posts;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.lastPage = lastPage;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public boolean hasPrev() {
        return hasPrev;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public int getLastPage() {
        return lastPage;
    }
}
