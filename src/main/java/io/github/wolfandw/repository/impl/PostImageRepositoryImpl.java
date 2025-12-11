package io.github.wolfandw.repository.impl;

import io.github.wolfandw.model.PostImage;
import io.github.wolfandw.repository.PostImageRepository;
import io.github.wolfandw.repository.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Primary
@Repository
public class PostImageRepositoryImpl implements PostImageRepository {
    private static final String JPG = "jpg";
    private final String uploadPostsDir;
    private final PostRepository postRepository;

    public PostImageRepositoryImpl(@Value("${my.blog.back.app.upload.posts.dir}") String uploadPostsDir, PostRepository postRepository) {
        this.uploadPostsDir = uploadPostsDir;
        this.postRepository = postRepository;
    }

    @Override
    public PostImage getPostImage(Long postId) {
        try {
            Path uploadDir = Paths.get(uploadPostsDir);
            if (Files.exists(uploadDir)) {
                Optional<String> imageName = postRepository.getImage(postId);
                if (imageName.isPresent()) {
                    Path imagePath = uploadDir.resolve(imageName.get()).normalize();
                    if (Files.exists(imagePath)) {
                        byte[] content = Files.readAllBytes(imagePath);
                        return new PostImage(content, getMediaType(content), postId);
                    }
                }
            }
        } catch (IOException e) {
            //throw new RuntimeException(e.getMessage(), e);
        }
        return new PostImage(new byte[0], MediaType.APPLICATION_OCTET_STREAM, postId);
    }

    @Override
    public void updatePostImage(Long postId, MultipartFile image) {
        try {
            Path uploadDir = Paths.get(uploadPostsDir);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            String originName = image.getOriginalFilename();
            String extension = getImageExtension(originName);
            String imageName = postId.toString() + "." + extension;
            Path imagePath = uploadDir.resolve(imageName);
            image.transferTo(imagePath);
            postRepository.setImage(postId, imageName);
        } catch (IOException e) {
            //throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void deletePostImage(Long postId) {
        try {
            Path uploadDir = Paths.get(uploadPostsDir);
            if (Files.exists(uploadDir)) {
                Optional<String> imageName = postRepository.getImage(postId);
                if (imageName.isPresent()) {
                    Path imagePath = uploadDir.resolve(imageName.get()).normalize();
                    if (Files.exists(imagePath)) {
                        Files.delete(imagePath);
                    }
                }
            }
        } catch (IOException e) {
            //throw new RuntimeException(e.getMessage(), e);
        }
    }

    private MediaType getMediaType(byte[] content) {
        if (content != null) {
            if (content.length >= 3 && content[0] == (byte) 0xFF && content[1] == (byte) 0xD8) {
                return MediaType.IMAGE_JPEG;
            }
            if (content.length >= 8 && content[0] == (byte) 0x89 && content[1] == 0x50) {
                return MediaType.IMAGE_PNG;
            }
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    private String getImageExtension(String originName) {
        if (originName != null && originName.lastIndexOf('.') != -1) {
            String extension = originName.substring(originName.lastIndexOf('.') + 1).toLowerCase();
            if (!extension.isEmpty()) {
                return extension;
            }
        }
        return JPG;
    }
}
