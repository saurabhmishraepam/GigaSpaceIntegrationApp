package com.epam.gigaSpaceIntegration.util;

import com.epam.gigaSpaceIntegration.constant.QueryConstants;

public class QueryBuilder {

    public static String queryBuilder(QueryConstants queryConst, String key, String value ){

        StringBuilder sb=new StringBuilder(key);
        sb.append(queryConst.toString());
        sb.append(value);
        return sb.toString();
    }
}
