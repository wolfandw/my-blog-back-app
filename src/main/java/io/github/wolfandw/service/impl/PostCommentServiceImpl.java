package io.github.wolfandw.service.impl;

import io.github.wolfandw.model.PostComment;
import io.github.wolfandw.repository.PostCommentRepository;
import io.github.wolfandw.repository.PostRepository;
import io.github.wolfandw.service.PostCommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    public PostCommentServiceImpl(PostRepository postRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public List<PostComment> getComments(Long postId) {
        return postCommentRepository.getComments(postId);
    }

    @Override
    public Optional<PostComment> getComment(Long postId, Long commentId) {
        return postCommentRepository.getComment(postId, commentId);
    }

    @Override
    public Optional<PostComment> createComment(Long postId, String text) {
        Optional<PostComment> comment = postCommentRepository.createComment(postId, text);
        if (comment.isPresent()) {
            postRepository.increaseCommentCount(postId);
        }
        return comment;
    }

    @Override
    public Optional<PostComment> updateComment(Long postId, Long commentId, String text) {
        return postCommentRepository.updateComment(postId, commentId, text);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        postCommentRepository.deleteComment(postId, commentId);
        postRepository.decreaseCommentCount(postId);
    }
}
