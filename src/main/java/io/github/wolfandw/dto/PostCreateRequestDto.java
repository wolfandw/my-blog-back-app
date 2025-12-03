package io.github.wolfandw.dto;

import java.util.List;

public record PostCreateRequestDto(String title,
                                   String text,
                                   List<String> tags) {
}
