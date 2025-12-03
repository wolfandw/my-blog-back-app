package io.github.wolfandw.controller;

import io.github.wolfandw.dto.PostCreateRequestDto;
import io.github.wolfandw.dto.PostListResponseDto;
import io.github.wolfandw.dto.PostResponseDto;
import io.github.wolfandw.dto.PostUpdateRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    @GetMapping
    public PostListResponseDto getPosts(
            @RequestParam("search") String search,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            PostResponseDto post = new PostResponseDto(
                    i,
                    "Post #" + i,
                    "Post #" + i + " text",
                    List.of("tag_1", "tag_2"),
                    25,
                    3 );

            postResponseDtoList.add(post);
        }
        return new PostListResponseDto(postResponseDtoList, true, false, 3);
    }

    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable("id") Long id) {
        return new PostResponseDto(
                id,
                "Post #" + id,
                "Post #" + id + " text",
                List.of("tag_1", "tag_2"),
                25,
                3 );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost(@RequestBody PostCreateRequestDto postRequest) {
        PostResponseDto newPostResponseDto = new PostResponseDto(
                99L,
                "Post #" + 99,
                "Post #" + 99 + " text",
                List.of("tag_1", "tag_2"),
                0,
                0 );
        return newPostResponseDto;
    }

    @PutMapping("/{id}")
    public PostResponseDto updatePost(
            @PathVariable("id") Long id,
            @RequestBody PostUpdateRequestDto postRequest) {
        PostResponseDto updatedPostResponseDto = new PostResponseDto(
                99L,
                "Post #" + 99,
                "Post #" + 99 + " text",
                List.of("tag_1", "tag_2"),
                0,
                0);
        return updatedPostResponseDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable("id") Long id) {
        //return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/likes")
    public Integer addLike(@PathVariable("id") Long id) {
        int likesCount = 7;
        return likesCount;
    }
}