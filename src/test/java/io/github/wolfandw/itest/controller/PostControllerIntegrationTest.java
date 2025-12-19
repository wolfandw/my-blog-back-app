package io.github.wolfandw.itest.controller;

import io.github.wolfandw.controller.PostController;
import io.github.wolfandw.itest.AbstractPostIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Интеграционный тест контроллера {@link PostController}
 */
public class PostControllerIntegrationTest extends AbstractPostIntegrationTest {
    @Test
    void getPostsPageTest() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .param("search", "")
                        .param("pageNumber", "1")
                        .param("pageSize", "15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts", hasSize(13)))
                .andExpect(jsonPath("$.hasPrev").value(false))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.lastPage").value(1))
        ;
    }

    @Test
    void getPostTest() throws Exception {
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Post 01 title"))
                .andExpect(jsonPath("$.text").value("Post 01 text for 128-length string testtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt128..."));
    }

    @Test
    void createPostTest() throws Exception {
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                   "title": "Post 14 title",
                                   "text": "Post 14 text",
                                   "tags": ["tag_3", "tag_4"]
                                 }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Post 14 title"))
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.tags[0]").value("tag_3"))
                .andExpect(jsonPath("$.tags[1]").value("tag_4"));
    }

    @Test
    void updatePostTest() throws Exception {
        mockMvc.perform(put("/api/posts/12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 12,
                                  "title": "Post 12 title update",
                                  "text": "Post 12 text update",
                                  "tags": ["tag_33", "tag_44", "tag_55"]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12))
                .andExpect(jsonPath("$.title").value("Post 12 title update"))
                .andExpect(jsonPath("$.text").value("Post 12 text update"))
                .andExpect(jsonPath("$.tags", hasSize(3)))
                .andExpect(jsonPath("$.tags[0]").value("tag_33"))
                .andExpect(jsonPath("$.tags[1]").value("tag_44"))
                .andExpect(jsonPath("$.tags[2]").value("tag_55"));
    }

    @Test
    void deletePostTest() throws Exception {
        mockMvc.perform(delete("/api/posts/13")).andExpect(status().isOk());
        mockMvc.perform(get("/api/posts/13")).andExpect(status().isNotFound());
    }

    @Test
    void increasePostLikesCountTest() throws Exception {
        mockMvc.perform(post("/api/posts/12/likes"))
                .andExpect(status().isOk())
                .andExpect(content().string("13"));

        mockMvc.perform(post("/api/posts/12/likes"))
                .andExpect(status().isOk())
                .andExpect(content().string("14"));
    }
}
