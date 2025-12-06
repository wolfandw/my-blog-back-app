package io.github.wolfandw.repository.impl;

import io.github.wolfandw.model.Post;
import io.github.wolfandw.repository.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private static final List<Post> POST_REPOSITORY = new ArrayList<>();
    private static Long maxId = 1L;

    public PostRepositoryImpl() {
        IntStream.range(1, 4).forEach(i ->
                createPost(
                        "Post " + i + " title",
                        "Post " + i + "  text",
                        List.of("tag_1", "tag_2"))
                        .ifPresent(post -> {
                            post.setCommentsCount(i);
                            post.setLikesCount(i);
                            POST_REPOSITORY.add(post);
                        }));
    }

    @Override
    public List<Post> getPosts(String search, int pageNumber, int pageSize) {
        return new ArrayList<>(POST_REPOSITORY);
    }

    @Override
    public Optional<Post> getPost(Long id) {
        return POST_REPOSITORY.stream().filter(post -> post.getId().equals(id)).findAny();
    }

    @Override
    public Optional<Post> createPost(String title, String text, List<String> tags) {
        return Optional.of(new Post(
                maxId++,
                title,
                text,
                tags,
                0,
                0));
    }

    @Override
    public Optional<Post> updatePost(Long id, String title, String text, List<String> tags) {
        Optional<Post> post = getPost(id);
        post.ifPresent(p -> {
            p.setTitle(title);
            p.setText(text);
            p.setTags(tags);
        });
        return post;
    }

    @Override
    public void deletePost(Long id) {
        POST_REPOSITORY.removeIf(post -> post.getId().equals(id));
    }

    @Override
    public int increaseLikesCount(Long id) {
        Optional<Post> post = getPost(id);
        post.ifPresent(p -> p.setLikesCount(p.getLikesCount() + 1));
        return post.map(Post::getLikesCount).orElse(-1);
    }

    @Override
    public void increaseCommentCount(Long id) {
        Optional<Post> post = getPost(id);
        post.ifPresent(p -> p.setCommentsCount(p.getCommentsCount() + 1));
    }

    @Override
    public void decreaseCommentCount(Long id) {
        Optional<Post> post = getPost(id);
        post.ifPresent(p -> p.setCommentsCount(p.getCommentsCount() - 1));
    }

    @Override
    public int getPostsCount() {
        return POST_REPOSITORY.size();
    }
}

