package io.github.wolfandw.controller;

import io.github.wolfandw.service.PostImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
public class PostImageController {
    private final PostImageService postImageService;

    public PostImageController(PostImageService postImageService) {
        this.postImageService = postImageService;
    }

    @GetMapping("/{postId}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable("postId") Long postId) {
        return postImageService.getPostImage(postId);
    }

    @PutMapping("/{postId}/image")
    @ResponseStatus(HttpStatus.OK)
    public void updatePostImage(
            @PathVariable("postId") Long postId,
            @RequestParam("image") MultipartFile image) {
        postImageService.updatePostImage(postId, image);
    }
}
