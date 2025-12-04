package io.github.wolfandw.service.impl;

import io.github.wolfandw.dto.PostImageResponseDto;
import io.github.wolfandw.service.PostImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class PostImageServiceImpl implements PostImageService {
    public static final Map<Long, PostImageResponseDto> POST_IMAGE_REPOSITORY = new HashMap<>();

    public PostImageServiceImpl() {
        IntStream.range(1, 4).forEach(i ->
                POST_IMAGE_REPOSITORY.put((long) i, new PostImageResponseDto(new byte[]{}, MediaType.IMAGE_PNG)));
    }

    @Override
    public ResponseEntity<byte[]> getPostImage(Long postId) {
        PostImageResponseDto imageResponse = POST_IMAGE_REPOSITORY.get(postId);
        return ResponseEntity.ok()
                .contentType(imageResponse.mediaType())
                .body(imageResponse.data());
    }

    @Override
    public void updatePostImage(Long postId, MultipartFile image) {
    }

    @Override
    public void deletePostImage(Long postId) {
        POST_IMAGE_REPOSITORY.remove(postId);
    }
}
