package com.epam.gigaSpaceIntegration.service;

import com.epam.gigaSpaceIntegration.config.XAPConfiguration;
import com.j_spaces.core.LeaseContext;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class GSCacheServiceImpl<T> implements CacheService<T> {

    private static final Logger logger = LoggerFactory.getLogger(GSCacheServiceImpl.class);
    private XAPConfiguration xapConfiguration = new XAPConfiguration();
    private GigaSpace gigaSpace;

    public GSCacheServiceImpl() {
        gigaSpace = xapConfiguration.gigaSpaceFactory();
    }

    @Override
    public void write(T t) {
        LeaseContext<T> context = gigaSpace.write(t);
        if (context.getVersion() == 1) {
            logger.info("write - " + t);
        } else {
            logger.info("update - " + t);
        }
    }

    @Override
    public Optional<T> readById(T t, int id) {
        return Optional.of((T) gigaSpace.readById(t.getClass(), id));
    }

    @Override
    public void deletePerson(T t) {
        gigaSpace.clear(t);
    }

    @Override
    public T[] readAll(T t) {
        T[] results = gigaSpace.readMultiple(t);
        return results;
    }


    public Optional<T> readByQuery(SQLQuery<T> query) {
        T result = gigaSpace.read(query);
        return Optional.of(result);
    }

    public T[] readMultipleByQuery(SQLQuery<T> query) {
        T[] result = gigaSpace.readMultiple(query);
        return result;
    }


}
