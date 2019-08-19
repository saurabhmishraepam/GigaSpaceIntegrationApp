package com.epam.gigaspaceintegration.util;

import com.epam.gigaspaceintegration.constant.QueryConstants;

/**
 * define the logic to build the query and validate against predefined rules
 */
public class QueryBuilder {

    public static String queryBuilder(QueryConstants queryConst, String key, String value ){

        // add query validity checks

        StringBuilder sb=new StringBuilder(key);
        sb.append(queryConst.toString());
        sb.append(value);
        return sb.toString();
    }

}
