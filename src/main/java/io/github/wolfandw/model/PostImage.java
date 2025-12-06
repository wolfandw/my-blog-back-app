package io.github.wolfandw.model;

import org.springframework.http.MediaType;

public class PostImage {
    private byte[] data;
    private MediaType mediaType;
    private final Long postId;

    public PostImage(byte[] data, MediaType mediaType, Long postId) {
        this.data = data;
        this.mediaType = mediaType;
        this.postId = postId;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public Long getPostId() {
        return postId;
    }
}
