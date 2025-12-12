package io.github.wolfandw.repository.impl;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.Nullable;

public class GeneratedIdKeyHolder extends GeneratedKeyHolder {
    @Override
    @Nullable
    public <T> T getKeyAs(Class<T> keyType) throws InvalidDataAccessApiUsageException, DataRetrievalFailureException {
        if (!getKeyList().isEmpty()) {
            Object key = getKeyList().getFirst().get("ID");
            if (key != null && keyType.isAssignableFrom(key.getClass())) {
                return keyType.cast(key);
            }
        }
        return null;
    }
}
