package com.epam.gigaSpaceIntegration.service;

import com.epam.gigaSpaceIntegration.constant.GSGridModeConstant;
import com.epam.gigaSpaceIntegration.config.XAPConfiguration;
import com.epam.gigaSpaceIntegration.constant.QueryConstants;
import com.epam.gigaSpaceIntegration.constant.XAPSpaceConstant;
import com.j_spaces.core.LeaseContext;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class GSCacheQueryServiceImpl<T> implements CacheQueryService<T> {

    private static final Logger logger = LoggerFactory.getLogger(GSCacheQueryServiceImpl.class);
    // this can be autowired
    private GigaSpace gigaSpace;
    public GSCacheQueryServiceImpl(GSGridModeConstant mode, XAPSpaceConstant xapSpacedetailes) {
        gigaSpace = new XAPConfiguration().gigaSpaceFactory(mode, xapSpacedetailes);
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

    @Override
    public Optional<T> readByQuery(T t, String key, String value) {

        return readByQuery(new SQLQuery(t.getClass(), key + QueryConstants.EQ, value));

    }

    private Optional<T> readByQuery(SQLQuery<T> query) {
        T result = gigaSpace.read(query);
        return Optional.of(result);
    }

    @Override
    public T[] readMultipleByQuery(T t, String query) {
        T[] result = (T[]) readMultipleByQuery(new SQLQuery(t.getClass(), query));
        return result;
    }

    private T[] readMultipleByQuery(SQLQuery<T> query) {
        T[] result = gigaSpace.readMultiple(query);
        return result;
    }


}