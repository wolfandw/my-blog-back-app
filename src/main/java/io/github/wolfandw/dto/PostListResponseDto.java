package io.github.wolfandw.dto;

import java.util.List;

public record PostListResponseDto(List<PostResponseDto> posts,
                                  boolean hasPrev,
                                  boolean hasNext,
                                  int lastPage) {
}
