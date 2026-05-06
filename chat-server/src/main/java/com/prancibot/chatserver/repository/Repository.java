package com.prancibot.chatserver.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Repository<E, T> {
    Optional<E> findById(T id);

    List<E> findByConditions(Map<String, String> conditions, int limit, int offset);

    List<E> findAll(int limit, int offset);

    void save(E entity);
}
