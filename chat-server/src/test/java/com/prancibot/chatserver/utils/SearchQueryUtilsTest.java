package com.prancibot.chatserver.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class SearchQueryUtilsTest {
    @Test
    void generateWhereClausesQueryBuildsExpectedQuery() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("name", "Alpha");
        values.put("status", "active");

        String query = SearchQueryUtils.generateWhereClausesQuery(DemoEntity.class, values);

        assertEquals("SELECT c FROM DemoEntity c WHERE c.name = :name c.status = :status ", query);
    }

    @Test
    void generateWhereClausesQueryUsesEntitySimpleName() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("id", "123");

        String query = SearchQueryUtils.generateWhereClausesQuery(DemoEntity.class, values);

        assertTrue(query.startsWith("SELECT c FROM DemoEntity c WHERE"));
    }

    private static final class DemoEntity {
    }
}