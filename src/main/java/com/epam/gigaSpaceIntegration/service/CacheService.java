package com.epam.gigaSpaceIntegration.service;



import java.util.Optional;

public interface CacheService<T>{

    void write(T t);
    Optional<T> readById(T t, int id);
    void deletePerson(T t);
    T [] readAll(T t);

}
