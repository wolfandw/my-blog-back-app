package io.github.wolfandw.itest.controller;

import io.github.wolfandw.itest.AbstractPostIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PostCommentControllerIntegrationTest extends AbstractPostIntegrationTest {
    @Test
    void getPostCommentsTest() throws Exception {
        mockMvc.perform(get("/api/posts/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].text").value("Post 1 comment 1"))
                .andExpect(jsonPath("$[0].postId").value("1"))
        ;
    }

    @Test
    void getPostCommentTest() throws Exception {
        mockMvc.perform(get("/api/posts/1/comments/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.text").value("Post 1 comment 3"))
                .andExpect(jsonPath("$.postId").value("1"));
    }

    @Test
    void createPostCommentTest() throws Exception {
        mockMvc.perform(post("/api/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "text": "Post 1 comment 4",
                                        "postId": 1
                                    }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.text").value("Post 1 comment 4"))
                .andExpect(jsonPath("$.postId").value("1"));
    }

    @Test
    void updatePostCommentTest() throws Exception {
        mockMvc.perform(put("/api/posts/1/comments/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "id": 3,
                                        "text": "Post 1 comment 3 update",
                                        "postId": 1
                                    }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.text").value("Post 1 comment 3 update"))
                .andExpect(jsonPath("$.postId").value("1"));
    }

    @Test
    void deletePostCommentTest() throws Exception {
        mockMvc.perform(delete("/api/posts/1/comments/3")).andExpect(status().isOk());
    }
}
