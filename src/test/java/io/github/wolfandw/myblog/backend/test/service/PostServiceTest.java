package io.github.wolfandw.myblog.backend.test.service;

import io.github.wolfandw.myblog.backend.model.Post;
import io.github.wolfandw.myblog.backend.model.PostsPage;
import io.github.wolfandw.myblog.backend.repository.PostCommentRepository;
import io.github.wolfandw.myblog.backend.repository.PostRepository;
import io.github.wolfandw.myblog.backend.service.PostService;
import io.github.wolfandw.myblog.backend.test.AbstractPostTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Модульный тест сервиса постов.
 */
public class PostServiceTest extends AbstractPostTest {
    @Autowired
    @Qualifier("postServiceTest")
    private PostService postServiceTest;

    @Autowired
    @Qualifier("postRepositoryTest")
    private PostRepository postRepositoryTest;

    @Autowired
    @Qualifier("postCommentRepositoryTest")
    private PostCommentRepository postCommentRepositoryTest;

    private Map<Long, Post> posts = new TreeMap<>();

    @BeforeEach
    void setUp() {
        reset(postRepositoryTest);
        reset(postCommentRepositoryTest);

        posts = LongStream.range(1, 16).boxed().collect(TreeMap::new, (m, postId) ->
                m.put(postId, new Post(postId, "Test Post " + postId + " title",
                        "Test Post " + postId + "  text",
                        List.of("tag_1", "tag_2"),
                        postId.intValue(),
                        postId.intValue(),
                        postId + ".png")), TreeMap::putAll);
    }

    @Test
    void testGetPostsPage() {
        int pageNumber = 2;
        int pageSize = 5;
        int lastPage = 3; // (int) Math.ceil((double) postsCount / pageSize);
        boolean hasPrev = true; // pageNumber > 1;
        boolean hasNext = true; // pageNumber < lastPage;

        List<Post> mockPosts = posts.values().stream()
                .filter(p -> p.getId() > ((pageNumber - 1) * pageSize) && p.getId() < (pageNumber * pageSize + 1))
                .toList();
        when(postRepositoryTest.getPosts(List.of(), List.of(), pageNumber, pageSize))
                .thenReturn(mockPosts);
        int postsCount = posts.size(); // 15
        when(postRepositoryTest.getPostsCount(List.of(), List.of())).thenReturn(postsCount);

        PostsPage postsPage = postServiceTest.getPostsPage("", pageNumber, pageSize);
        assertNotNull(postsPage, "Страница постов не может быть null");
        assertArrayEquals(mockPosts.toArray(), postsPage.getPosts().toArray(), "Список постов должен соответствовать исходному");
        assertEquals(hasPrev, postsPage.hasPrev(), "Наличие предыдущей страницы должено соответствовать исходному");
        assertEquals(hasNext, postsPage.hasNext(), "Наличие следующей страницы должено соответствовать исходному");
        assertEquals(lastPage, postsPage.getLastPage(), "Номер последней страницы должен соответствовать исходному");
    }

    @Test
    void testGetPostsPageWithIncorrectParameters() {
        int pageNumber = 2;
        int pageSize = 5;

        List<Post> mockPosts = posts.values().stream()
                .filter(p -> p.getId() > ((pageNumber - 1) * pageSize) && p.getId() < (pageNumber * pageSize + 1))
                .toList();
        when(postRepositoryTest.getPosts(List.of(), List.of(), pageNumber, pageSize))
                .thenReturn(mockPosts);
        int postsCount = posts.size(); // 15
        when(postRepositoryTest.getPostsCount(List.of(), List.of())).thenReturn(postsCount);

        PostsPage postsPage = postServiceTest.getPostsPage(null, -1, -1);
        assertNotNull(postsPage, "Страница постов не может быть null");
    }

    @Test
    void testGetPost() {
        Long postId = 5L;
        Post mockPost = posts.get(postId);
        when(postRepositoryTest.getPost(postId)).thenReturn(Optional.of(mockPost));
        Optional<Post> post = postServiceTest.getPost(postId);

        assertTrue(post.isPresent(), "Пост должен быть получен");

        Post postInstance = post.get();
        assertEquals("Test Post " + postId + " title", postInstance.getTitle(), "Название поста должно совпадать");
        assertEquals("Test Post " + postId + "  text", postInstance.getText(), "Текс поста должен совпадать");
        assertEquals(2, postInstance.getTags().size(), "Количество тэгов должно совпадать");
        assertEquals("tag_1", postInstance.getTags().get(0), "Имя тэга 1 должно совпадать");
        assertEquals("tag_2", postInstance.getTags().get(1), "Имя тэга 2 должно совпадать");
        assertEquals(postId, postInstance.getLikesCount(), "Количество лайков должно совпадать должно совпадать");
        assertEquals(postId, postInstance.getCommentsCount(), "Количество комментариев должно совпадать");
        assertEquals(postId + ".png", postInstance.getImageName(), "Имя картинки должно совпадать");
    }

    @Test
    void testCreatePost() {
        Long postId = 16L;
        String title = "Test Post " + postId + " title";
        String text = "Test Post " + postId + "  text";
        List<String> tags = List.of("tag_1", "tag_2");
        int likesCount = 0;
        int commentsCount = 0;
        String imageName = postId + ".png";
        Post mockPost = new Post(postId, title, text, tags, likesCount, commentsCount, imageName);
        when(postRepositoryTest.createPost(title, text, tags)).thenReturn(Optional.of(mockPost));
        Optional<Post> post = postServiceTest.createPost(title, text, tags);

        assertTrue(post.isPresent(), "Пост должен быть создан и получен");

        Post postInstance = post.get();
        assertEquals("Test Post " + postId + " title", postInstance.getTitle(), "Название поста должно совпадать");
        assertEquals("Test Post " + postId + "  text", postInstance.getText(), "Текс поста должен совпадать");
        assertEquals(tags.size(), postInstance.getTags().size(), "Количество тэгов должно совпадать");
        assertEquals("tag_1", postInstance.getTags().get(0), "Имя тэга 1 должно совпадать");
        assertEquals("tag_2", postInstance.getTags().get(1), "Имя тэга 2 должно совпадать");
        assertEquals(likesCount, postInstance.getLikesCount(), "Количество лайков должно совпадать должно совпадать");
        assertEquals(commentsCount, postInstance.getCommentsCount(), "Количество комментариев должно совпадать");
        assertEquals(imageName, postInstance.getImageName(), "Имя картинки должно совпадать");
    }

    @Test
    void testUpdatePost() {
        Long postId = 5L;
        String title = "Test Post " + postId + " title updated";
        String text = "Test Post " + postId + "  text updated";
        List<String> tags = List.of("tag_3", "tag_4", "tag_5");
        Post postBeforeUpdate = posts.get(postId);
        Post mockPost = new Post(postId,
                title,
                text,
                tags,
                postBeforeUpdate.getLikesCount(),
                postBeforeUpdate.getCommentsCount(),
                postBeforeUpdate.getImageName());
        when(postRepositoryTest.updatePost(postId, title, text, tags)).thenReturn(Optional.of(mockPost));
        Optional<Post> post = postServiceTest.updatePost(postId, title, text, tags);

        assertTrue(post.isPresent(), "Пост должен быть обновлен и получен");

        Post postInstance = post.get();
        assertEquals(title, postInstance.getTitle(), "Название поста должно быть изменено");
        assertEquals(text, postInstance.getText(), "Текс поста должен быть изменено");
        assertEquals(tags.size(), postInstance.getTags().size(), "Количество тэгов должно быть изменено");
        assertEquals("tag_3", postInstance.getTags().get(0), "Имя тэга 1 должно совпадать");
        assertEquals("tag_4", postInstance.getTags().get(1), "Имя тэга 2 должно совпадать");
        assertEquals("tag_5", postInstance.getTags().get(2), "Имя тэга 3 должно совпадать");
        assertEquals(postId, postInstance.getLikesCount(), "Количество лайков должно совпадать должно совпадать");
        assertEquals(postId, postInstance.getCommentsCount(), "Количество комментариев должно совпадать");
        assertEquals(postId + ".png", postInstance.getImageName(), "Имя картинки должно совпадать");
    }

    @Test
    void testDeletePost() {
        Long postId = 5L;
        doNothing().when(postCommentRepositoryTest).deletePostComments(postId);
        doNothing().when(postRepositoryTest).deletePost(postId);

        postServiceTest.deletePost(postId);

        verify(postCommentRepositoryTest).deletePostComments(postId);
        verify(postRepositoryTest).deletePost(postId);
    }

    @Test
    void testIncreasePostLikesCount() {
        Long postId = 5L;
        Post postBeforeUpdate = posts.get(postId);
        int beforeLikesCount = postBeforeUpdate.getLikesCount();
        when(postRepositoryTest.increasePostLikesCount(postId)).thenReturn(beforeLikesCount + 1);
        int likesCount = postServiceTest.increasePostLikesCount(postId);

        assertEquals(beforeLikesCount + 1, likesCount, "Количество лайков должно увеличиться на 1");
    }
}
