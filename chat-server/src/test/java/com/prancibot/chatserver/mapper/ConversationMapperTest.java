package com.prancibot.chatserver.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.model.Conversation;

class ConversationMapperTest {
    private final ConversationMapper mapper = Mappers.getMapper(ConversationMapper.class);

    @Test
    void toConversationMapsNameAndIgnoresId() {
        CreateConversationDTO dto = new CreateConversationDTO();
        dto.setName("Support");

        Conversation conversation = mapper.toConversation(dto);

        assertNull(conversation.getId());
        assertEquals("Support", conversation.getName());
    }

    @Test
    void toDtoMapsConversationFields() {
        Conversation conversation = new Conversation();
        conversation.setId(UUID.randomUUID());
        conversation.setName("Support");

        ConversationDTO dto = mapper.toDTO(conversation);

        assertEquals(conversation.getId(), dto.getId());
        assertEquals("Support", dto.getName());
    }

    @Test
    void toConversationDtoListMapsAllItems() {
        Conversation first = new Conversation();
        first.setId(UUID.randomUUID());
        first.setName("One");

        Conversation second = new Conversation();
        second.setId(UUID.randomUUID());
        second.setName("Two");

        List<ConversationDTO> conversations = mapper.toConversationDTOList(List.of(first, second));

        assertEquals(2, conversations.size());
        assertEquals("One", conversations.get(0).getName());
        assertEquals("Two", conversations.get(1).getName());
    }
}