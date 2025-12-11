package io.github.wolfandw.repository.test;

import io.github.wolfandw.model.PostImage;
import io.github.wolfandw.repository.PostImageRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

@Repository
public class PostImageRepositoryTest implements PostImageRepository {
    public static final Map<Long, PostImage> POST_IMAGE_REPOSITORY = new HashMap<>();

    public PostImageRepositoryTest() {
        IntStream.range(1, 4).forEach(i ->
                POST_IMAGE_REPOSITORY.put((long) i, new PostImage(new byte[]{}, MediaType.IMAGE_PNG, (long) i)));
    }

    @Override
    public PostImage getPostImage(Long postId) {
        return POST_IMAGE_REPOSITORY.getOrDefault(postId, new PostImage(new byte[0], MediaType.APPLICATION_OCTET_STREAM, postId));
    }

    @Override
    public void updatePostImage(Long postId, MultipartFile image) {
        PostImage postImage = POST_IMAGE_REPOSITORY.get(postId);
        if (postImage != null) {
            try {
                postImage.setData(image.getBytes());
                postImage.setMediaType(MediaType.parseMediaType(Objects.requireNonNull(image.getContentType())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deletePostImage(Long postId) {
        POST_IMAGE_REPOSITORY.remove(postId);
    }
}
