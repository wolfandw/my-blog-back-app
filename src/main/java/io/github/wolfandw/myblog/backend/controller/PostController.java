package io.github.wolfandw.myblog.backend.controller;

import io.github.wolfandw.myblog.backend.controller.mapper.PostToDtoMapper;
import io.github.wolfandw.myblog.backend.controller.mapper.PostsPageToDtoMapper;
import io.github.wolfandw.myblog.backend.dto.PostCreateRequestDto;
import io.github.wolfandw.myblog.backend.dto.PostResponseDto;
import io.github.wolfandw.myblog.backend.dto.PostUpdateRequestDto;
import io.github.wolfandw.myblog.backend.dto.PostsPageResponseDto;
import io.github.wolfandw.myblog.backend.model.PostsPage;
import io.github.wolfandw.myblog.backend.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Реализация контроллера постов.
 */
@RestController("postController")
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;
    private final PostToDtoMapper postToDtoMapper;
    private final PostsPageToDtoMapper postsPageToDtoMapper;

    /**
     * Создает контроллер постов.
     *
     * @param postService          сервис постов
     * @param postToDtoMapper      маппер поста в DTO поста
     * @param postsPageToDtoMapper маппер страницы постов в DTO страницы постов
     */
    public PostController(PostService postService, PostToDtoMapper postToDtoMapper, PostsPageToDtoMapper postsPageToDtoMapper) {
        this.postService = postService;
        this.postToDtoMapper = postToDtoMapper;
        this.postsPageToDtoMapper = postsPageToDtoMapper;
    }

    /**
     * Возвращает DTO страницы постов.
     *
     * @param search     параметр строки поиска
     * @param pageNumber параметр номера страницы постов
     * @param pageSize   параметр размера страницы постов
     * @return DTO страницы постов.
     */
    @GetMapping
    public PostsPageResponseDto getPostsPage(
            @RequestParam("search") String search,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize) {
        PostsPage postsPage = postService.getPostsPage(search, pageNumber, pageSize);
        return postsPageToDtoMapper.mapPostsPageToPostsPageResponseDto(postsPage);
    }

    /**
     * Возвращает DTO поста
     *
     * @param id идентификатор поста
     * @return DTO поста
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") Long id) {
        return postService.getPost(id).map(postToDtoMapper::mapPostToPostResponseDto)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Создает пост.
     *
     * @param postRequestDto DTO запроса создания поста
     * @return DTO нового поста
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost(@RequestBody PostCreateRequestDto postRequestDto) {
        return postService.createPost(postRequestDto.title(), postRequestDto.text(), postRequestDto.tags()).map(postToDtoMapper::mapPostToPostResponseDto).orElse(null);
    }

    /**
     * Обновляет пост.
     *
     * @param id          идентификатор поста
     * @param postRequest DTO запроса на обновление поста
     * @return DTO обновленного поста
     */
    @PutMapping("/{id}")
    public PostResponseDto updatePost(
            @PathVariable("id") Long id,
            @RequestBody PostUpdateRequestDto postRequest) {
        return postService.updatePost(id, postRequest.title(), postRequest.text(), postRequest.tags()).map(postToDtoMapper::mapPostToPostResponseDto).orElse(null);
    }

    /**
     * Удаляет пост.
     *
     * @param id идентификатор поста
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }

    /**
     * Увеличивает количество лайков поста на единицу.
     *
     * @param id идентификатор поста
     * @return новое значение колчества лайков
     */
    @PostMapping("/{id}/likes")
    public Integer increasePostLikesCount(@PathVariable("id") Long id) {
        return postService.increasePostLikesCount(id);
    }
}