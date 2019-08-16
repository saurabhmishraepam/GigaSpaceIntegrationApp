package com.epam.gigaSpaceIntegration.service;

import java.util.Optional;

public interface CacheQueryService<T> extends CacheService<T> {

    Optional<T> readByQuery(T t, String key, String value);

    T[] readMultipleByQuery(T t, String query);

}
