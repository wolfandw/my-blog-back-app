package io.github.wolfandw.service.impl;

import io.github.wolfandw.model.Post;
import io.github.wolfandw.model.PostsPage;
import io.github.wolfandw.repository.PostCommentRepository;
import io.github.wolfandw.repository.PostImageRepository;
import io.github.wolfandw.repository.PostRepository;
import io.github.wolfandw.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostCommentRepository postCommentRepository;

    public PostServiceImpl(PostRepository postRepository, PostImageRepository postImageRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public PostsPage getPostsPage(String search, int pageNumber, int pageSize) {
        List<Post> posts = postRepository.getPosts(search, pageNumber, pageSize);
        int postsCount = postRepository.getPostsCount();
        return new PostsPage(posts, postsCount);
    }

    @Override
    public Optional<Post> getPost(Long postId) {
        return postRepository.getPost(postId);
    }

    @Override
    public Optional<Post> createPost(String title, String text, List<String> tags) {
        return postRepository.createPost(title, text, tags);
    }

    @Override
    public Optional<Post> updatePost(Long postId, String title, String text, List<String> tags) {
        return postRepository.updatePost(postId, title, text, tags);
    }

    @Override
    public void deletePost(Long postId) {
        postCommentRepository.deleteComments(postId);
        postImageRepository.deletePostImage(postId);
        postRepository.deletePost(postId);
    }

    @Override
    public int increaseLikesCount(Long postId) {
        return postRepository.increaseLikesCount(postId);
    }
}
