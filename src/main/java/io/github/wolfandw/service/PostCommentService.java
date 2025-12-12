package io.github.wolfandw.service;

import io.github.wolfandw.model.PostComment;

import java.util.List;
import java.util.Optional;

public interface PostCommentService {
    List<PostComment> getPostComments(Long postId);

    Optional<PostComment> getPostComment(Long postId, Long commentId);

    Optional<PostComment> createPostComment(Long postId, String text);

    Optional<PostComment> updatePostComment(Long postId, Long commentId, String text);

    void deletePostComment(Long postId, Long commentId);
}
