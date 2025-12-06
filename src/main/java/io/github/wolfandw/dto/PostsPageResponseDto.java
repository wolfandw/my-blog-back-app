package io.github.wolfandw.dto;

import java.util.List;

public record PostsPageResponseDto(List<PostResponseDto> posts,
                                   boolean hasPrev,
                                   boolean hasNext,
                                   int lastPage) {
}
