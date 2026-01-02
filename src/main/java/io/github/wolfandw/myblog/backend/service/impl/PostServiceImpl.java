package io.github.wolfandw.myblog.backend.service.impl;

import io.github.wolfandw.myblog.backend.model.Post;
import io.github.wolfandw.myblog.backend.model.PostsPage;
import io.github.wolfandw.myblog.backend.repository.PostCommentRepository;
import io.github.wolfandw.myblog.backend.repository.PostRepository;
import io.github.wolfandw.myblog.backend.service.PostImageService;
import io.github.wolfandw.myblog.backend.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link PostService}
 */
@Service("postService")
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostImageService postImageService;
    private final PostCommentRepository postCommentRepository;

    /**
     * Создает сервис для работы с постами.
     *
     * @param postRepository        репозиторий постов
     * @param postImageService      сервис картинок постов
     * @param postCommentRepository репозиторий комментариев постов
     */
    public PostServiceImpl(PostRepository postRepository,
                           PostImageService postImageService,
                           PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postImageService = postImageService;
        this.postCommentRepository = postCommentRepository;
    }

    @Transactional
    @Override
    public PostsPage getPostsPage(String search, int pageNumber, int pageSize) {
        List<String> searchWords = new ArrayList<>();
        List<String> tags = new ArrayList<>();

        if (search != null) {
            for (String word : search.split("\\s+")) {
                if (word.startsWith("#")) {
                    tags.add(word.substring(1));
                } else if (!word.isEmpty()) {
                    searchWords.add(word);
                }
            }
        }

        pageSize = Math.max(pageSize, 1);
        pageNumber = Math.max(pageNumber, 1);

        List<Post> posts = postRepository.getPosts(searchWords, tags, pageNumber, pageSize);
        int postsCount = postRepository.getPostsCount(searchWords, tags);

        int lastPage = (int) Math.ceil((double) postsCount / pageSize);
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = pageNumber < lastPage;

        return new PostsPage(posts, hasPrev, hasNext, lastPage);
    }

    @Transactional
    @Override
    public Optional<Post> getPost(Long postId) {
        return postRepository.getPost(postId);
    }

    @Transactional
    @Override
    public Optional<Post> createPost(String title, String text, List<String> tags) {
        return postRepository.createPost(title, text, tags);
    }

    @Transactional
    @Override
    public Optional<Post> updatePost(Long postId, String title, String text, List<String> tags) {
        return postRepository.updatePost(postId, title, text, tags);
    }

    @Transactional
    @Override
    public void deletePost(Long postId) {
        postCommentRepository.deletePostComments(postId);
        postImageService.deletePostImage(postId);
        postRepository.deletePost(postId);
    }

    @Transactional
    @Override
    public int increasePostLikesCount(Long postId) {
        return postRepository.increasePostLikesCount(postId);
    }
}
