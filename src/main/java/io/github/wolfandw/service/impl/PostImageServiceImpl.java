package io.github.wolfandw.service.impl;

import io.github.wolfandw.model.PostImage;
import io.github.wolfandw.repository.PostImageRepository;
import io.github.wolfandw.service.PostImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class PostImageServiceImpl implements PostImageService {
    private final PostImageRepository postImageRepository;

    public PostImageServiceImpl(PostImageRepository postImageRepository) {
        this.postImageRepository = postImageRepository;
    }

    @Override
    public Optional<PostImage> getPostImage(Long postId) {
        return postImageRepository.getPostImage(postId);
    }

    @Override
    public void updatePostImage(Long postId, MultipartFile image) {
        postImageRepository.updatePostImage(postId, image);
    }
}
