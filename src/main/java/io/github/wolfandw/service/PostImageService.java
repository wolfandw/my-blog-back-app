package io.github.wolfandw.service;

import io.github.wolfandw.model.PostImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PostImageService {
    Optional<PostImage> getPostImage(Long postId);

    void updatePostImage(Long postId, MultipartFile image);
}
