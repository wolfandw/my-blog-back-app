package io.github.wolfandw.repository.impl;

import java.util.List;

public class RepositoryUtil {
    public static int[] toIntArray(List<Integer> integers) {
        return integers.stream().mapToInt(Integer::intValue).toArray();
    }

    private RepositoryUtil() {
        // Do nothing
    }
}
