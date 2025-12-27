package io.github.wolfandw.myblog.backend.service.impl;

import io.github.wolfandw.myblog.backend.model.PostImage;
import io.github.wolfandw.myblog.backend.repository.PostImageRepository;
import io.github.wolfandw.myblog.backend.service.FileStorageService;
import io.github.wolfandw.myblog.backend.service.PostImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Реализация {@link PostImageService}
 */
@Service
public class PostImageServiceImpl implements PostImageService {
    private static final String JPG = "jpg";
    private static final Logger log = LoggerFactory.getLogger(PostImageServiceImpl.class);
    private final PostImageRepository postImageRepository;
    private final FileStorageService fileStorageService;

    /**
     * Создает сервис для работы с картинками постов.
     *
     * @param postImageRepository репозиторий для работы с картинками постов
     * @param fileStorageService  сервис работы с файлами
     */
    public PostImageServiceImpl(PostImageRepository postImageRepository, FileStorageService fileStorageService) {
        this.postImageRepository = postImageRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    @Override
    public PostImage getPostImage(Long postId) {
        Optional<String> imageName = postImageRepository.getPostImageName(postId);
        return imageName.map(fileName -> {
            try {
                byte[] content = fileStorageService.readFile(fileName);
                return new PostImage(content, getMediaType(content), postId);
            } catch (IOException e) {
                log.error("Картинка {} поста {} не загрузилась", fileName, postId, e);
                return null;
            }
        }).orElse(new PostImage(new byte[0], MediaType.APPLICATION_OCTET_STREAM, postId));
    }

    @Transactional
    @Override
    public void updatePostImage(Long postId, MultipartFile image) {
        if (image == null) {
            return;
        }
        String originName = image.getOriginalFilename();
        String extension = getImageExtension(originName);
        String imageName = postId.toString() + "." + extension;
        try {
            fileStorageService.writeFile(imageName, image);
            postImageRepository.updatePostImageName(postId, imageName);
        } catch (IOException e) {
            log.error("Картинка {} поста {} не обновилась", imageName, postId, e);
        }
    }

    @Transactional
    @Override
    public void deletePostImage(Long postId) {
        Optional<String> imageName = postImageRepository.getPostImageName(postId);
        imageName.ifPresent(fileName -> {
            try {
                fileStorageService.deleteFile(fileName);
                postImageRepository.updatePostImageName(postId, null);
            } catch (IOException e) {
                log.error("Картинка {} поста {} не удалилась", fileName, postId, e);
            }
        });
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
