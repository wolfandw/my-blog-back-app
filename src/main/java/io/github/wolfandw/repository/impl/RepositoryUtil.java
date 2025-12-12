package io.github.wolfandw.repository.impl;

import java.util.List;

public class RepositoryUtil {
    private RepositoryUtil() {
        // Do nothing
    }

    public static int[] toIntArray(List<Integer> integers) {
        return integers.stream().mapToInt(Integer::intValue).toArray();
    }
}
