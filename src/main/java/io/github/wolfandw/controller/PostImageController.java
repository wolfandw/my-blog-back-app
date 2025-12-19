package io.github.wolfandw.controller;

import io.github.wolfandw.model.PostImage;
import io.github.wolfandw.service.PostImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Реализация контроллера картинок постов.
 */
@RestController
@RequestMapping("/api/posts")
public class PostImageController {
    private final PostImageService postImageService;

    /**
     * Создание контроллера картинок постов.
     *
     * @param postImageService сервис картинок постов
     */
    public PostImageController(PostImageService postImageService) {
        this.postImageService = postImageService;
    }

    /**
     * Возвращает картинку поста.
     *
     * @param postId идентификатор поста
     * @return ответ с массивом байт картинки
     */
    @GetMapping("/{postId}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable("postId") Long postId) {
        PostImage postImage = postImageService.getPostImage(postId);
        return ResponseEntity.ok().contentType(postImage.getMediaType()).body(postImage.getData());
    }

    /**
     * Обновляет картинку поста.
     *
     * @param postId идентификатор поста
     * @param image  файл картинки
     */
    @PutMapping("/{postId}/image")
    @ResponseStatus(HttpStatus.OK)
    public void updatePostImage(
            @PathVariable("postId") Long postId,
            @RequestParam("image") MultipartFile image) {
        postImageService.updatePostImage(postId, image);
    }
}
