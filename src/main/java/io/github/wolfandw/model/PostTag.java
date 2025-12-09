package io.github.wolfandw.model;

public class PostTag {
    private final Long postId;
    private final String name;

    public PostTag(Long postId, String name) {
        this.postId = postId;
        this.name = name;
    }

    public Long getPostId() {
        return postId;
    }

    public String getName() {
        return name;
    }
}
