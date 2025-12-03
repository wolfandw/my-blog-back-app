package io.github.wolfandw.dto;

import java.util.List;

public record PostUpdateRequestDto(Long id,
                                   String title,
                                   String text,
                                   List<String> tags) {
}
