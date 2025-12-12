package io.github.wolfandw.controller;

import io.github.wolfandw.controller.mapper.PostToDtoMapper;
import io.github.wolfandw.dto.PostCreateRequestDto;
import io.github.wolfandw.dto.PostResponseDto;
import io.github.wolfandw.dto.PostUpdateRequestDto;
import io.github.wolfandw.dto.PostsPageResponseDto;
import io.github.wolfandw.model.Post;
import io.github.wolfandw.model.PostsPage;
import io.github.wolfandw.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;
    private final PostToDtoMapper postToDtoMapper;

    public PostController(PostService postService, PostToDtoMapper postToDtoMapper) {
        this.postService = postService;
        this.postToDtoMapper = postToDtoMapper;
    }

    @GetMapping
    public PostsPageResponseDto getPosts(
            @RequestParam("search") String search,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize) {
        PostsPage postsPage = postService.getPostsPage(search, pageNumber, pageSize);
        List<Post> postOnPage = postsPage.getPosts();
        int postsCount = postsPage.getPostsCount();

        int lastPage = (int) Math.ceil((double) postsCount / pageSize);
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = pageNumber < lastPage;

        return new PostsPageResponseDto(postOnPage.stream().map(postToDtoMapper::mapPostToPostResponseDto).toList(), hasPrev, hasNext, lastPage);
    }

    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable("id") Long id) {
        return postService.getPost(id).map(postToDtoMapper::mapPostToPostResponseDto).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost(@RequestBody PostCreateRequestDto postRequest) {
        return postService.createPost(postRequest.title(), postRequest.text(), postRequest.tags()).map(postToDtoMapper::mapPostToPostResponseDto).orElse(null);
    }

    @PutMapping("/{id}")
    public PostResponseDto updatePost(
            @PathVariable("id") Long id,
            @RequestBody PostUpdateRequestDto postRequest) {
        return postService.updatePost(id, postRequest.title(), postRequest.text(), postRequest.tags()).map(postToDtoMapper::mapPostToPostResponseDto).orElse(null);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }

    @PostMapping("/{id}/likes")
    public Integer increasePostLikesCount(@PathVariable("id") Long id) {
        return postService.increasePostLikesCount(id);
    }
}