package io.github.wolfandw.itest.controller;

import io.github.wolfandw.itest.AbstractPostIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostImageControllerTest extends AbstractPostIntegrationTest {
    @Test
    void getPostsPageTest() throws Exception {
        mockMvc.perform(get("/api/posts/1/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(new byte[0]));
    }

    @Test
    void updatePostImageTest() throws Exception {
        mockMvc.perform(multipart("/api/posts/1/image")
                        .file(new MockMultipartFile("image",
                                "1.jpg",
                                MediaType.IMAGE_JPEG_VALUE,
                                new byte[]{1, 2, 3}))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("PUT");
                            return requestPostProcessor;
                        }))
                .andExpect(status().isOk());
    }
}
