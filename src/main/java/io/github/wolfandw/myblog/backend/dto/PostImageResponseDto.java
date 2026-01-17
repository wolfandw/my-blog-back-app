package io.github.wolfandw.myblog.backend.dto;

import org.springframework.http.MediaType;

/**
 * DTO-описание картинки поста.
 *
 * @param data      данные картинки поста
 * @param mediaType тип данных
 */
public record PostImageResponseDto(byte[] data, MediaType mediaType) {
}
