package io.github.wolfandw.dto;

import java.util.List;

public record PostResponseDto(Long id,
                              String title,
                              String text,
                              List<String> tags,
                              Integer likesCount,
                              Integer commentsCount) {
}
