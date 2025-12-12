package io.github.wolfandw.repository.test;

import io.github.wolfandw.model.Post;
import io.github.wolfandw.repository.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
public class PostRepositoryTest implements PostRepository {
    private static final List<Post> POST_REPOSITORY = new ArrayList<>();
    private static Long maxId = 1L;

    public PostRepositoryTest() {
        IntStream.range(1, 4).forEach(i ->
                createPost(
                        "Test Post " + i + " title",
                        "Test Post " + i + "  text",
                        List.of("tag_1", "tag_2"))
                        .ifPresent(post -> {
                            post.setCommentsCount(i);
                            post.setLikesCount(i);
                            post.setImageName(i + ".png");
                            POST_REPOSITORY.add(post);
                        }));
    }

    @Override
    public List<Post> getPosts(List<String> searchWords, List<String> tags, int pageNumber, int pageSize) {
        return new ArrayList<>(POST_REPOSITORY);
    }

    @Override
    public Optional<Post> getPost(Long postId) {
        return POST_REPOSITORY.stream().filter(post -> post.getId().equals(postId)).findAny();
    }

    @Override
    public Optional<Post> createPost(String title, String text, List<String> tags) {
        return Optional.of(new Post(
                maxId++,
                title,
                text,
                tags,
                0,
                0,
                ""));
    }

    @Override
    public Optional<Post> updatePost(Long postId, String title, String text, List<String> tags) {
        Optional<Post> post = getPost(postId);
        post.ifPresent(p -> {
            p.setTitle(title);
            p.setText(text);
            p.setTags(tags);
        });
        return post;
    }

    @Override
    public void deletePost(Long postId) {
        POST_REPOSITORY.removeIf(post -> post.getId().equals(postId));
    }

    @Override
    public int increasePostLikesCount(Long postId) {
        Optional<Post> post = getPost(postId);
        post.ifPresent(p -> p.setLikesCount(p.getLikesCount() + 1));
        return post.map(Post::getLikesCount).orElse(-1);
    }

    @Override
    public void increasePostCommentCount(Long postId) {
        Optional<Post> post = getPost(postId);
        post.ifPresent(p -> p.setCommentsCount(p.getCommentsCount() + 1));
    }

    @Override
    public void decreasePostCommentCount(Long postId) {
        Optional<Post> post = getPost(postId);
        post.ifPresent(p -> p.setCommentsCount(p.getCommentsCount() - 1));
    }

    @Override
    public int getPostsCount(List<String> searchWords, List<String> tags) {
        return POST_REPOSITORY.size();
    }

    @Override
    public void updatePostImageName(Long postId, String imageName) {
        Optional<Post> post = getPost(postId);
        post.ifPresent(p -> p.setImageName(imageName));
    }

    @Override
    public Optional<String> getPostImageName(Long postId) {
        Optional<Post> post = getPost(postId);
        return post.map(Post::getImageName);
    }
}

