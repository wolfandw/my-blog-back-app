package io.github.wolfandw.service;

import io.github.wolfandw.model.Post;
import io.github.wolfandw.model.PostsPage;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostsPage getPostsPage(String search, int pageNumber, int pageSize);

    Optional<Post> getPost(Long postId);

    Optional<Post> createPost(String title, String text, List<String> tags);

    Optional<Post> updatePost(Long postId, String title, String text, List<String> tags);

    void deletePost(Long postId);

    int increasePostLikesCount(Long postId);
}
