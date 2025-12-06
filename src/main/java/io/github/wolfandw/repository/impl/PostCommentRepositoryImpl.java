package io.github.wolfandw.repository.impl;

import io.github.wolfandw.model.PostComment;
import io.github.wolfandw.repository.PostCommentRepository;
import io.github.wolfandw.repository.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.IntStream;

@Repository
public class PostCommentRepositoryImpl implements PostCommentRepository {
    public static final Map<Long, List<PostComment>> POST_COMMENT_REPOSITORY = new HashMap<>();
    private static Long maxId = 1L;

    private final PostRepository postRepository;

    public PostCommentRepositoryImpl(PostRepository postRepository) {
        this.postRepository = postRepository;

        IntStream.range(1, 4).forEach(i -> {
            Long postId = (long) i;
            List<PostComment> postComments = POST_COMMENT_REPOSITORY.computeIfAbsent(postId, v -> new ArrayList<>());
            IntStream.range(0, i).forEach(j ->
                    createComment(postId, "Post " + postId + ", comment " + (maxId + 1)).ifPresent(postComments::add));
        });
    }

    @Override
    public List<PostComment> getComments(Long postId) {
        return POST_COMMENT_REPOSITORY.computeIfAbsent(postId, v -> new ArrayList<>());
    }

    @Override
    public Optional<PostComment> getComment(Long postId, Long commentId) {
        List<PostComment> postComments = POST_COMMENT_REPOSITORY.computeIfAbsent(postId, v -> new ArrayList<>());
        return postComments.stream().filter(comment -> comment.getId().equals(commentId)).findAny();
    }

    @Override
    public Optional<PostComment> createComment(Long postId, String text) {
        PostComment postComment = new PostComment(maxId++,
                text,
                postId);
        return Optional.of(postComment);
    }

    @Override
    public Optional<PostComment> updateComment(Long postId, Long commentId, String text) {
        Optional<PostComment> postComment = getComment(postId, commentId);
        postComment.ifPresent(comment -> comment.setText(text));
        return postComment;
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        List<PostComment> postComment = POST_COMMENT_REPOSITORY.computeIfAbsent(postId, v -> new ArrayList<>());
        postComment.removeIf(comment -> comment.getId().equals(commentId));
        postRepository.decreaseCommentCount(postId);
    }

    @Override
    public void deleteComments(Long postId) {
        POST_COMMENT_REPOSITORY.remove(postId);
    }
}
