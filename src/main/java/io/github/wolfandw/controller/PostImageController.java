package io.github.wolfandw.controller;

import io.github.wolfandw.dto.ImageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
public class PostImageController {
    @GetMapping("/{postId}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable("postId") Long postId) {
        ImageResponseDto imageResponse = new ImageResponseDto(new byte[]{1, 2, 3}, MediaType.IMAGE_PNG) ;
        return ResponseEntity.ok()
                .contentType(imageResponse.mediaType())
                .body(imageResponse.data());
    }

    @PutMapping("/{postId}/image")
    @ResponseStatus(HttpStatus.OK)
    public void updatePostImage(
            @PathVariable("postId") Long postId,
            @RequestParam("image") MultipartFile image) {
    }
}
