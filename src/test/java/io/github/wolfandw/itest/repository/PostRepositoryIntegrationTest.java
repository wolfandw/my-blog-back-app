package io.github.wolfandw.itest.repository;

import io.github.wolfandw.itest.AbstractPostIntegrationTest;
import io.github.wolfandw.model.Post;
import io.github.wolfandw.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционный тест репозитория {@link PostRepository}
 */
public class PostRepositoryIntegrationTest extends AbstractPostIntegrationTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    void getPostsTest() {
        List<Post> posts = postRepository.getPosts(List.of(), List.of(), 1, 15);

        assertNotNull(posts, "Спикок постов не должен быть нулл");
        assertEquals(13, posts.size(), "Размер тестового списка постов должен быть равен указанному");
        Post postInstance = posts.getFirst();
        assertEquals(13L, postInstance.getId(), "Идентификатор первого поста должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 13 title", postInstance.getTitle(), "Заголовок поста должен соответствовать указанному");
        assertEquals("Post 13 text", postInstance.getText(), "Текст поста должен соответствовать указанному");
        List<String> tags = postInstance.getTags();
        assertEquals(3, tags.size(), "Количество тэгов должно соответствовать указанному");
        assertEquals("Tag1", tags.get(0), "Имя тэга 1 должно совпадать");
        assertEquals("Tag2", tags.get(1), "Имя тэга 2 должно совпадать");
        assertEquals("Tag3", tags.get(2), "Имя тэга 3 должно совпадать");
        assertEquals(13, postInstance.getLikesCount(), "Количество лайков должно совпадать");
        assertEquals(3, postInstance.getCommentsCount(), "количество комментариев должно совпадать");
        assertEquals("13.png", postInstance.getImageName(), "Имя картинки должно совпадать");
    }

    @ParameterizedTest
    @MethodSource("provideGetPostsEmptyArgs")
    void getPostsPageNotExistTest(List<String> searchWords, List<String> tags, int pageNumber, int pageSize) {
        List<Post> posts = postRepository.getPosts(searchWords, tags, pageNumber, pageSize);

        assertNotNull(posts, "Спикок постов не должен быть нулл");
        assertEquals(0, posts.size(), "Размер тестового списка постов должен быть равен указанному");
    }

    private static Stream<Arguments> provideGetPostsEmptyArgs() {
        return Stream.of(
                Arguments.of(List.of(), List.of(), 2, 15),
                Arguments.of(List.of(), List.of(), 0, 0),
                Arguments.of(List.of(), List.of(), -1, -1)
        );
    }

    @Test
    void getPostTest() {
        Optional<Post> post = postRepository.getPost(1L);

        assertTrue(post.isPresent(), "Пост не должен быть пустым");
        Post postInstance = post.get();
        assertEquals(1L, postInstance.getId(), "Идентификатор первого поста должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 01 title", postInstance.getTitle(), "Заголовок поста должен соответствовать указанному");
        assertEquals("Post 01 text for 128-length string testtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt128qq",
                postInstance.getText(), "Текст поста должен соответствовать указанному");
        List<String> tags = postInstance.getTags();
        assertEquals(1, tags.size(), "Количество тэгов должно соответствовать указанному");
        assertEquals("Tag1", tags.getFirst(), "Имя тэга 1 должно совпадать");
        assertEquals(1, postInstance.getLikesCount(), "Количество лайков должно совпадать");
        assertEquals(3, postInstance.getCommentsCount(), "количество комментариев должно совпадать");
        assertEquals("1.png", postInstance.getImageName(), "Имя картинки должно совпадать");
    }

    @ParameterizedTest
    @ValueSource(longs = {14L, 0L, -1L})
    void getPostEmptyTest(Long postId) {
        Optional<Post> post = postRepository.getPost(postId);

        assertTrue(post.isEmpty(), "Пост должен быть пустым");
    }

    @Test
    void createPostTest() {
        Optional<Post> post = postRepository.createPost("Post 14 title", "Post 14 text", List.of("Tag4", "Tag5", "Tag6"));

        assertTrue(post.isPresent(), "Пост не должен быть пустым");
        Post postInstance = post.get();
        assertEquals(14L, postInstance.getId(), "Идентификатор первого поста должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 14 title", postInstance.getTitle(), "Заголовок поста должен соответствовать указанному");
        assertEquals("Post 14 text", postInstance.getText(), "Текст поста должен соответствовать указанному");
        List<String> tags = postInstance.getTags();
        assertEquals(3, tags.size(), "Количество тэгов должно соответствовать указанному");
        assertEquals("Tag4", tags.get(0), "Имя тэга 1 должно совпадать");
        assertEquals("Tag5", tags.get(1), "Имя тэга 2 должно совпадать");
        assertEquals("Tag6", tags.get(2), "Имя тэга 3 должно совпадать");
        assertEquals(0, postInstance.getLikesCount(), "Количество лайков должно 0 у нового поста");
        assertEquals(0, postInstance.getCommentsCount(), "количество комментариев должно быть 0 у нового поста");
        assertNull(postInstance.getImageName(), "Имя картинки должно быть нулл у нового поста");
    }

    @Test
    void updatePostTest() {
        Optional<Post> postBefore = postRepository.getPost(2L);
        assertTrue(postBefore.isPresent(), "Пост до обновления не должен быть пустым");

        Optional<Post> post = postRepository.updatePost(2L, "Post 02 title updated", "Post 02 text updated", List.of("Tag4", "Tag5", "Tag6"));

        assertTrue(post.isPresent(), "Пост после обнвления не должен быть пустым");
        Post postInstance = post.get();
        assertEquals(postBefore.get().getId(), postInstance.getId(), "Идентификатор первого поста должен быть равен указанному, т.к. сортировка");
        assertEquals("Post 02 title updated", postInstance.getTitle(), "Заголовок поста должен соответствовать указанному");
        assertEquals("Post 02 text updated", postInstance.getText(), "Текст поста должен соответствовать указанному");
        List<String> tags = postInstance.getTags();
        assertEquals(3, tags.size(), "Количество тэгов должно соответствовать указанному");
        assertEquals("Tag4", tags.get(0), "Имя тэга 1 должно совпадать");
        assertEquals("Tag5", tags.get(1), "Имя тэга 2 должно совпадать");
        assertEquals("Tag6", tags.get(2), "Имя тэга 3 должно совпадать");
        assertEquals(postBefore.get().getLikesCount(), postInstance.getLikesCount(), "Количество лайков должно 0 у нового поста");
        assertEquals(postBefore.get().getCommentsCount(), postInstance.getCommentsCount(), "количество комментариев должно быть 0 у нового поста");
        assertEquals(postBefore.get().getImageName(), postInstance.getImageName(), "Имя картинки должно быть нулл у нового поста");
    }

    @Test
    void deletePostTest() {
        Optional<Post> postBefore = postRepository.getPost(13L);
        assertTrue(postBefore.isPresent(), "Пост до удаления должен быть");

        postRepository.deletePost(13L);

        Optional<Post> post = postRepository.getPost(13L);
        assertTrue(post.isEmpty(), "Пост после удаления не должен быть");
    }

    @Test
    void increasePostLikesCountTest() {
        Optional<Post> postBefore = postRepository.getPost(13L);
        assertTrue(postBefore.isPresent(), "Пост до изменения должен быть");
        int likesBefore = postBefore.get().getLikesCount();

        postRepository.increasePostLikesCount(13L);

        Optional<Post> post = postRepository.getPost(13L);
        assertTrue(post.isPresent(), "Пост после изменения должен быть");
        assertEquals(likesBefore + 1, post.get().getLikesCount(), "Количество лайков должно увеличится на 1");
    }

    @Test
    void increasePostCommentCountTest() {
        Optional<Post> postBefore = postRepository.getPost(13L);
        assertTrue(postBefore.isPresent(), "Пост до изменения должен быть");
        int commentsBefore = postBefore.get().getCommentsCount();

        postRepository.increasePostCommentCount(13L);

        Optional<Post> post = postRepository.getPost(13L);
        assertTrue(post.isPresent(), "Пост после изменения должен быть");
        assertEquals(commentsBefore + 1, post.get().getCommentsCount(), "Количество комментариев должно увеличится на 1");
    }

    @Test
    void decreasePostCommentCountTest() {
        Optional<Post> postBefore = postRepository.getPost(13L);
        assertTrue(postBefore.isPresent(), "Пост до изменения должен быть");
        int commentsBefore = postBefore.get().getCommentsCount();

        postRepository.decreasePostCommentCount(13L);

        Optional<Post> post = postRepository.getPost(13L);
        assertTrue(post.isPresent(), "Пост после изменения должен быть");
        assertEquals(Math.max(commentsBefore - 1, 0), post.get().getCommentsCount(), "Количество комментариев должно уменьшится на 1 или в 0");
    }

    @Test
    void getPostsCountTest() {
        int postCount = postRepository.getPostsCount(List.of(), List.of());

        assertEquals(13, postCount, "Общее количество постов должно совпадать");
    }

    @Test
    void updatePostImageNameTest() {
        Optional<Post> postBefore = postRepository.getPost(13L);
        assertTrue(postBefore.isPresent(), "Пост до изменения должен быть");
        assertEquals("13.png", postBefore.get().getImageName(), "Имя картинки должно совпадать");

        postRepository.updatePostImageName(13L, "133.jpg");

        Optional<Post> post = postRepository.getPost(13L);
        assertTrue(post.isPresent(), "Пост после изменения должен быть");
        assertEquals("133.jpg", post.get().getImageName(), "Новое имя картинки должно быть установлено");
    }

    @Test
    void getPostImageNameTest() {
        Optional<Post> post = postRepository.getPost(13L);

        assertTrue(post.isPresent(), "Пост должен быть");
        assertEquals("13.png", post.get().getImageName(), "Имя картинки должно совпадать");
    }
}
