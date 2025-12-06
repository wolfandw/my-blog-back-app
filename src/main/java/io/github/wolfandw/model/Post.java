package io.github.wolfandw.model;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private final Long id;
    private String title;
    private String text;
    private List<String> tags = new ArrayList<>();
    private int likesCount;
    private int commentsCount;

    public Post(Long id,
                String title,
                String text,
                List<String> tags,
                int likesCount,
                int commentsCount) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}