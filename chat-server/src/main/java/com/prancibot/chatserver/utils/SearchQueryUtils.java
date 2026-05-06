package com.prancibot.chatserver.utils;

import java.util.Map;

public class SearchQueryUtils {
    public static String generateWhereClausesQuery(Class<?> entityType, Map<String, String> values) {
        String template = "c.%s = :%s";
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT c FROM %s c WHERE".formatted(entityType.getSimpleName()));
        queryBuilder.append(" ");
        values.forEach((key, _) -> {
            queryBuilder.append(template.formatted(key, key));
            queryBuilder.append(" ");
        });
        return queryBuilder.toString();
    }
}
