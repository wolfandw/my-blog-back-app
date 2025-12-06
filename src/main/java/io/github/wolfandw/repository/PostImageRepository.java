package io.github.wolfandw.repository;

import io.github.wolfandw.model.PostImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PostImageRepository {
    Optional<PostImage> getPostImage(Long postId);

    void updatePostImage(Long postId, MultipartFile image);

    void deletePostImage(Long postId);
}
