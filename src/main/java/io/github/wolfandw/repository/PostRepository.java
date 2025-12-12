package io.github.wolfandw.repository;

import io.github.wolfandw.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> getPosts(List<String> searchWords, List<String> tags, int pageNumber, int pageSize);

    Optional<Post> getPost(Long postId);

    Optional<Post> createPost(String title, String text, List<String> tags);

    Optional<Post> updatePost(Long postId, String title, String text, List<String> tags);

    void deletePost(Long postId);

    int increasePostLikesCount(Long postId);

    void increasePostCommentCount(Long postId);

    void decreasePostCommentCount(Long postId);

    int getPostsCount(List<String> searchWords, List<String> tags);

    void updatePostImageName(Long postId, String imageName);

    Optional<String> getPostImageName(Long postId);
}

