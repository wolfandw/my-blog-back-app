package io.github.wolfandw.service.impl;

import io.github.wolfandw.dto.PostCreateRequestDto;
import io.github.wolfandw.dto.PostListResponseDto;
import io.github.wolfandw.dto.PostResponseDto;
import io.github.wolfandw.dto.PostUpdateRequestDto;
import io.github.wolfandw.service.PostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PostServiceImpl implements PostService {
    public static final List<PostResponseDto> POST_REPOSITORY = new ArrayList<>();
    private static Long maxId = 1L;

    public PostServiceImpl() {
        IntStream.range(1, 4).forEach(i ->
                POST_REPOSITORY.add(createPost(new PostCreateRequestDto(
                        "Post " + i + " title",
                        "Post " + i + "  text",
                        List.of("tag_1", "tag_2")))));
    }

    @Override
    public PostListResponseDto getPosts(String search, int pageNumber, int pageSize) {
        return new PostListResponseDto(POST_REPOSITORY, false, false, 1);
    }

    @Override
    public PostResponseDto getPost(Long id) {
        for (PostResponseDto postResponseDto : POST_REPOSITORY) {
            if (postResponseDto.id().equals(id)) {
                return postResponseDto;
            }
        }
        return null;
    }

    @Override
    public PostResponseDto createPost(PostCreateRequestDto postRequest) {
        PostResponseDto postResponseDto = new PostResponseDto(
                maxId,
                postRequest.title(),
                postRequest.text(),
                postRequest.tags(),
                0,
                0);
        maxId++;
        return postResponseDto;
    }

    @Override
    public PostResponseDto updatePost(Long id, PostUpdateRequestDto postRequest) {
        PostResponseDto source = getPost(id);
        PostResponseDto target = new PostResponseDto(
                source.id(),
                postRequest.title(),
                postRequest.text(),
                postRequest.tags(),
                source.likesCount(),
                source.commentsCount());
        POST_REPOSITORY.set(target.id().intValue() - 1, target);
        return target;
    }

    @Override
    public void deletePost(Long id) {
        PostCommentServiceImpl.POST_COMMENT_REPOSITORY.get(id).removeIf(comment -> comment.postId().equals(id));
        PostImageServiceImpl.POST_IMAGE_REPOSITORY.remove(id);
        POST_REPOSITORY.removeIf(post -> post.id().equals(id));
    }

    @Override
    public int increaseLikesCount(Long id) {
        PostResponseDto source = getPost(id);
        PostResponseDto target = new PostResponseDto(
                source.id(),
                source.title(),
                source.text(),
                source.tags(),
                source.likesCount() + 1,
                source.commentsCount());
        POST_REPOSITORY.set(target.id().intValue() - 1, target);
        return target.likesCount();
    }

    @Override
    public void increaseCommentCount(Long id) {
        PostResponseDto source = getPost(id);
        PostResponseDto target = new PostResponseDto(
                source.id(),
                source.title(),
                source.text(),
                source.tags(),
                source.likesCount(),
                source.commentsCount() + 1);
        POST_REPOSITORY.set(target.id().intValue() - 1, target);
    }

    @Override
    public void decreaseCommentCount(Long id) {
        PostResponseDto source = getPost(id);
        PostResponseDto target = new PostResponseDto(
                source.id(),
                source.title(),
                source.text(),
                source.tags(),
                source.likesCount(),
                source.commentsCount() - 1);
        POST_REPOSITORY.set(target.id().intValue() - 1, target);
    }
}
