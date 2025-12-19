package io.github.wolfandw.dto;

import java.util.List;

/**
 * DTO-описание страницы постов.
 *
 * @param posts    список постов
 * @param hasPrev  есть предыдущая страница
 * @param hasNext  есть следующая страница
 * @param lastPage номер последней страницы
 */
public record PostsPageResponseDto(List<PostResponseDto> posts,
                                   boolean hasPrev,
                                   boolean hasNext,
                                   int lastPage) {
}
