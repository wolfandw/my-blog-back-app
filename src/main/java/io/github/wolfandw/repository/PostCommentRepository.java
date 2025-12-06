package io.github.wolfandw.repository;

import io.github.wolfandw.model.PostComment;

import java.util.List;
import java.util.Optional;

public interface PostCommentRepository {
    List<PostComment> getComments(Long postId);

    Optional<PostComment> getComment(Long postId, Long commentId);

    Optional<PostComment> createComment(Long postId, String text);

    Optional<PostComment> updateComment(Long postId, Long commentId, String text);

    void deleteComment(Long postId, Long commentId);

    void deleteComments(Long postId);
}
