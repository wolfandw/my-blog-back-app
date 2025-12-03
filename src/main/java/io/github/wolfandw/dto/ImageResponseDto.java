package io.github.wolfandw.dto;

import org.springframework.http.MediaType;

public record ImageResponseDto(byte[] data, MediaType mediaType) {
}
