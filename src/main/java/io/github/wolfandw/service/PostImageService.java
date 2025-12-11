package io.github.wolfandw.service;

import io.github.wolfandw.model.PostImage;
import org.springframework.web.multipart.MultipartFile;

public interface PostImageService {
    PostImage getPostImage(Long postId);

    void updatePostImage(Long postId, MultipartFile image);
}
