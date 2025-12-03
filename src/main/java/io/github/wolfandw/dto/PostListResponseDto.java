package io.github.wolfandw.dto;

import java.util.List;

public record PostListResponseDto(List<PostResponseDto> posts,
                                  Boolean hasPrev,
                                  Boolean hasNext,
                                  Integer lastPage) {
}
