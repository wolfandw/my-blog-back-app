package io.github.wolfandw.repository;

import io.github.wolfandw.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> getPosts(String search, int pageNumber, int pageSize);

    Optional<Post> getPost(Long id);

    Optional<Post> createPost(String title, String text, List<String> tags);

    Optional<Post> updatePost(Long id, String title, String text, List<String> tags);

    void deletePost(Long id);

    int increaseLikesCount(Long id);

    void increaseCommentCount(Long id);

    void decreaseCommentCount(Long id);

    int getPostsCount();
}

