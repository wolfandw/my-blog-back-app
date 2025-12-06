package io.github.wolfandw.model;

import java.util.ArrayList;
import java.util.List;

public class PostsPage {
    private final int postsCount;
    private List<Post> posts = new ArrayList<>();

    public PostsPage(List<Post> posts, int postsCount) {
        this.posts = posts;
        this.postsCount = postsCount;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public int getPostsCount() {
        return postsCount;
    }
}
