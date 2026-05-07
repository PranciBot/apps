package com.prancibot.chatserver.rs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.pagination.PaginationParam;
import com.prancibot.chatserver.service.ConversationStoreService;

import jakarta.ws.rs.core.Response;

class ConversationResourceTest {
    private FakeConversationStoreService service;
    private ConversationResource resource;

    @BeforeEach
    void setUp() {
        service = new FakeConversationStoreService();
        resource = new ConversationResource(service);
    }

    @Test
    void createConversationReturnsCreatedResponse() {
        CreateConversationDTO dto = new CreateConversationDTO();
        dto.setName("Support");

        Response response = resource.createConversation(dto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("/conversations/" + service.lastCreatedId, response.getLocation().getPath());
    }

    @Test
    void updateConversationReturnsUpdatedDto() {
        UUID id = UUID.randomUUID();
        CreateConversationDTO dto = new CreateConversationDTO();
        dto.setName("Updated");

        Response response = resource.updateConversation(id, dto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        ConversationDTO entity = (ConversationDTO) response.getEntity();
        assertEquals(id, entity.getId());
        assertEquals("Updated", entity.getName());
    }

    @Test
    void deleteConversationReturnsNoContent() {
        Response response = resource.deleteConversation(UUID.randomUUID());

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void searchConversationBlankNameReturnsEmptyList() {
        Response response = resource.searchConversation("   ", new PaginationParam(10, 0));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(List.of(), response.getEntity());
    }

    private static final class FakeConversationStoreService implements ConversationStoreService {
        private UUID lastCreatedId;

        @Override
        public ConversationDTO create(CreateConversationDTO dto) {
            ConversationDTO conversation = new ConversationDTO();
            conversation.setId(UUID.randomUUID());
            conversation.setName(dto.getName());
            lastCreatedId = conversation.getId();
            return conversation;
        }

        @Override
        public ConversationDTO update(UUID id, CreateConversationDTO dto) {
            ConversationDTO conversation = new ConversationDTO();
            conversation.setId(id);
            conversation.setName(dto.getName());
            return conversation;
        }

        @Override
        public void delete(UUID id) {
        }

        @Override
        public List<ConversationDTO> getAllConversation(PaginationParam param) {
            return List.of();
        }

        @Override
        public List<ConversationDTO> searchByName(String name, PaginationParam param) {
            return List.of();
        }
    }
}