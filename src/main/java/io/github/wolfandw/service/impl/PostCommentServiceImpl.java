package io.github.wolfandw.service.impl;

import io.github.wolfandw.model.PostComment;
import io.github.wolfandw.repository.PostCommentRepository;
import io.github.wolfandw.repository.PostRepository;
import io.github.wolfandw.service.PostCommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link PostCommentService}
 */
@Service
public class PostCommentServiceImpl implements PostCommentService {
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    /**
     * Создает сервис для работы с комментариями постов.
     *
     * @param postRepository        репозиторий постов
     * @param postCommentRepository репозиторий комментариев постов
     */
    public PostCommentServiceImpl(PostRepository postRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public List<PostComment> getPostComments(Long postId) {
        return postCommentRepository.getPostComments(postId);
    }

    @Override
    public Optional<PostComment> getPostComment(Long postId, Long commentId) {
        return postCommentRepository.getPostComment(postId, commentId);
    }

    @Override
    public Optional<PostComment> createPostComment(Long postId, String text) {
        Optional<PostComment> comment = postCommentRepository.createPostComment(postId, text);
        comment.ifPresent(c -> postRepository.increasePostCommentCount(postId));
        return comment;
    }

    @Override
    public Optional<PostComment> updatePostComment(Long postId, Long commentId, String text) {
        return postCommentRepository.updatePostComment(postId, commentId, text);
    }

    @Override
    public void deletePostComment(Long postId, Long commentId) {
        postRepository.decreasePostCommentCount(postId);
        postCommentRepository.deletePostComment(postId, commentId);
    }
}
