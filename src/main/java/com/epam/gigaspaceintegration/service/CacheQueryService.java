package com.epam.gigaspaceintegration.service;

import java.util.Optional;

/**
 * Interface to handle the query methods, client code independent
 * @param <T> T is the  type of object to do the query on
 */
public interface CacheQueryService<T> extends CacheService<T> {

    Optional<T> readByQuery(T t, String key, String value);

    T[] readMultipleByQuery(T t, String query);

}
