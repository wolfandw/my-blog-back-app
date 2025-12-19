package io.github.wolfandw.repository.impl;

import org.jspecify.annotations.Nullable;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.GeneratedKeyHolder;

/**
 * {@link GeneratedKeyHolder} только для полей "ID".
 */
public class GeneratedIdKeyHolder extends GeneratedKeyHolder {
    @Override
    public <T> @Nullable T getKeyAs(Class<T> keyType) throws InvalidDataAccessApiUsageException, DataRetrievalFailureException {
        if (!getKeyList().isEmpty()) {
            Object key = getKeyList().getFirst().get("ID");
            if (key != null && keyType.isAssignableFrom(key.getClass())) {
                return keyType.cast(key);
            }
        }
        return null;
    }
}
