package io.github.wolfandw.repository.impl;

import java.util.List;

/**
 * Утилитный класс для работы с репозиториями данных.
 */
public class RepositoryUtil {
    /**
     * Конфертирует список {@link Integer} в массив int[].
     *
     * @param integers список {@link Integer}
     * @return массив int[]
     */
    public static int[] toIntArray(List<Integer> integers) {
        return integers.stream().mapToInt(Integer::intValue).toArray();
    }

    private RepositoryUtil() {
        // Do nothing
    }
}
