package io.github.wolfandw.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PostImageService {
    ResponseEntity<byte[]> getPostImage(Long postId);

    void updatePostImage(Long postId, MultipartFile image);

    void deletePostImage(Long postId);
}
