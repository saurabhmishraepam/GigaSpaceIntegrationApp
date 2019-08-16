package com.epam.gigaSpaceIntegration.service;

import com.epam.gigaSpaceIntegration.bean.Person;
import com.j_spaces.core.client.SQLQuery;

import java.util.Optional;

public interface CacheQueryService<T> extends CacheService<T> {

    Optional<T> readByQuery(T t, String key, String value);
    T[] readMultipleByQuery(T t, String query);

}
