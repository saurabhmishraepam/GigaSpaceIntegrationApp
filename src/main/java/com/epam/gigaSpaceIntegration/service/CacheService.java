package com.epam.gigaSpaceIntegration.service;


import java.util.Optional;

/**
 * Default CRUD interface to support the cache operations
 * @param <T> define the type of object to use for cache
 */
public interface CacheService<T> {
    /*
        write for 2nd time is considered update
     */
    void write(T t);

    Optional<T> readById(T t, int id);

    void delete(T t);

    T[] readAll(T t);

    void deleteMultiple(T t,String query );
}
