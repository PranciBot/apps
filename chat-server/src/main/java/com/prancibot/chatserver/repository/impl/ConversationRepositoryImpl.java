package com.prancibot.chatserver.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.prancibot.chatserver.model.Conversation;
import com.prancibot.chatserver.pagination.PaginationParam;
import com.prancibot.chatserver.repository.ConversationRepository;
import com.prancibot.chatserver.utils.SearchQueryUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class ConversationRepositoryImpl implements ConversationRepository {
    private final EntityManager em;

    public ConversationRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Conversation> findById(UUID id) {
        Conversation conversation = em.find(Conversation.class, id);
        return Optional.ofNullable(conversation);
    }

    @Override
    public List<Conversation> findByName(String name, PaginationParam param) {
        return em.createQuery(
                "SELECT c FROM Conversation c WHERE c.name = :name",
                Conversation.class)
                .setParameter("name", name)
                .setFirstResult(param.getOffset())
                .setMaxResults(param.getLimit())
                .getResultList();
    }

    @Override
    public List<Conversation> findByConditions(Map<String, String> conditions, int limit, int offset) {
        String strQuery = SearchQueryUtils.generateWhereClausesQuery(Conversation.class, conditions);
        TypedQuery<Conversation> query = em.createQuery(strQuery, Conversation.class);
        conditions.forEach(query::setParameter);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<Conversation> findAll(int limit, int offset) {
        return em.createQuery(
                "SELECT c FROM Conversation c",
                Conversation.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public void save(Conversation conversation) {
        em.persist(conversation);
    }

    @Override
    public void deleteById(UUID id) {
        Optional.ofNullable(em.find(Conversation.class, id))
                .ifPresent(em::remove);
    }
}
