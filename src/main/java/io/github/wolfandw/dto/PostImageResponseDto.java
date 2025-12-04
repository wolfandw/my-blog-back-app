package io.github.wolfandw.dto;

import org.springframework.http.MediaType;

public record PostImageResponseDto(byte[] data, MediaType mediaType) {
}
