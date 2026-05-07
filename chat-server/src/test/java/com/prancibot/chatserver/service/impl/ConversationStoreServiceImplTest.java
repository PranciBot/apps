package com.prancibot.chatserver.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.mapper.ConversationMapper;
import com.prancibot.chatserver.model.Conversation;
import com.prancibot.chatserver.pagination.PaginationParam;
import com.prancibot.chatserver.repository.ConversationRepository;

import jakarta.ws.rs.NotFoundException;

class ConversationStoreServiceImplTest {
    private FakeConversationRepository repository;
    private ConversationStoreServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = new FakeConversationRepository();
        service = new ConversationStoreServiceImpl(new SimpleConversationMapper(), repository);
    }

    @Test
    void createConversationPersistsAndReturnsDto() {
        CreateConversationDTO dto = new CreateConversationDTO();
        dto.setName("Support");

        ConversationDTO created = service.create(dto);

        assertEquals("Support", created.getName());
        assertTrue(created.getId() != null);
        assertTrue(repository.findById(created.getId()).isPresent());
    }

    @Test
    void updateConversationChangesName() {
        Conversation existing = repository.storeConversation("Old name");

        CreateConversationDTO dto = new CreateConversationDTO();
        dto.setName("New name");

        ConversationDTO updated = service.update(existing.getId(), dto);

        assertEquals(existing.getId(), updated.getId());
        assertEquals("New name", updated.getName());
        assertEquals("New name", repository.findById(existing.getId()).orElseThrow().getName());
    }

    @Test
    void deleteConversationRemovesEntity() {
        Conversation existing = repository.storeConversation("To delete");

        service.delete(existing.getId());

        assertTrue(repository.findById(existing.getId()).isEmpty());
    }

    @Test
    void updateMissingConversationThrowsNotFound() {
        CreateConversationDTO dto = new CreateConversationDTO();
        dto.setName("Missing");

        assertThrows(NotFoundException.class, () -> service.update(UUID.randomUUID(), dto));
    }

    @Test
    void deleteMissingConversationThrowsNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(UUID.randomUUID()));
    }

    @Test
    void getAllConversationReturnsMappedDtos() {
        repository.storeConversation("One");
        repository.storeConversation("Two");

        List<ConversationDTO> conversations = service.getAllConversation(new PaginationParam(10, 0));

        assertEquals(2, conversations.size());
    }

    @Test
    void searchByNameReturnsMatchingConversation() {
        repository.storeConversation("Alpha");
        repository.storeConversation("Beta");

        List<ConversationDTO> conversations = service.searchByName("Alpha", new PaginationParam(10, 0));

        assertEquals(1, conversations.size());
        assertEquals("Alpha", conversations.get(0).getName());
    }

    private static final class SimpleConversationMapper implements ConversationMapper {
        @Override
        public Conversation toConversation(CreateConversationDTO dto) {
            Conversation conversation = new Conversation();
            conversation.setName(dto.getName());
            return conversation;
        }

        @Override
        public ConversationDTO toDTO(Conversation entity) {
            ConversationDTO dto = new ConversationDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            return dto;
        }

        @Override
        public List<ConversationDTO> toConversationDTOList(List<Conversation> list) {
            return list.stream().map(this::toDTO).toList();
        }
    }

    private static final class FakeConversationRepository implements ConversationRepository {
        private final Map<UUID, Conversation> conversations = new HashMap<>();

        Conversation storeConversation(String name) {
            Conversation conversation = new Conversation();
            conversation.setId(UUID.randomUUID());
            conversation.setName(name);
            conversations.put(conversation.getId(), conversation);
            return conversation;
        }

        @Override
        public Optional<Conversation> findById(UUID id) {
            return Optional.ofNullable(conversations.get(id));
        }

        @Override
        public List<Conversation> findByName(String name, PaginationParam param) {
            return findByConditions(Map.of("name", name), param.getLimit(), param.getOffset());
        }

        @Override
        public List<Conversation> findByConditions(Map<String, String> conditions, int limit, int offset) {
            String name = conditions.get("name");
            List<Conversation> matches = conversations.values().stream()
                    .filter(conversation -> conversation.getName() != null && conversation.getName().equals(name))
                    .toList();
            int start = Math.min(offset, matches.size());
            int end = Math.min(start + limit, matches.size());
            return new ArrayList<>(matches.subList(start, end));
        }

        @Override
        public List<Conversation> findAll(int limit, int offset) {
            List<Conversation> all = new ArrayList<>(conversations.values());
            int start = Math.min(offset, all.size());
            int end = Math.min(start + limit, all.size());
            return all.subList(start, end);
        }

        @Override
        public void save(Conversation entity) {
            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID());
            }
            conversations.put(entity.getId(), entity);
        }

        @Override
        public void deleteById(UUID id) {
            conversations.remove(id);
        }
    }
}