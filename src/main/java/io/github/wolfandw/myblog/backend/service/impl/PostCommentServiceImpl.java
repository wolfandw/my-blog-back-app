package io.github.wolfandw.myblog.backend.service.impl;

import io.github.wolfandw.myblog.backend.model.PostComment;
import io.github.wolfandw.myblog.backend.repository.PostCommentRepository;
import io.github.wolfandw.myblog.backend.repository.PostRepository;
import io.github.wolfandw.myblog.backend.service.PostCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link PostCommentService}
 */
@Service("postCommentService")
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

    @Transactional
    @Override
    public List<PostComment> getPostComments(Long postId) {
        return postCommentRepository.getPostComments(postId);
    }

    @Transactional
    @Override
    public Optional<PostComment> getPostComment(Long postId, Long commentId) {
        return postCommentRepository.getPostComment(postId, commentId);
    }

    @Transactional
    @Override
    public Optional<PostComment> createPostComment(Long postId, String text) {
        Optional<PostComment> comment = postCommentRepository.createPostComment(postId, text);
        comment.ifPresent(c -> postRepository.increasePostCommentCount(postId));
        return comment;
    }

    @Transactional
    @Override
    public Optional<PostComment> updatePostComment(Long postId, Long commentId, String text) {
        return postCommentRepository.updatePostComment(postId, commentId, text);
    }

    @Transactional
    @Override
    public void deletePostComment(Long postId, Long commentId) {
        postRepository.decreasePostCommentCount(postId);
        postCommentRepository.deletePostComment(postId, commentId);
    }
}
