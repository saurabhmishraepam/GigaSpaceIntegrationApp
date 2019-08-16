package com.epam.gigaSpaceIntegration.service;


import java.util.Optional;

/**
 *
 * @param <T> define the type of object to use for cache
 */
public interface CacheService<T>{
    void write(T t);
    Optional<T> readById(T t, int id);
    void deletePerson(T t);
    T [] readAll(T t);
}
