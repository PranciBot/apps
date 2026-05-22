package com.prancibot.chatserver.mapper;

import com.prancibot.chatserver.dto.ChatDTO;
import com.prancibot.chatserver.dto.CreateMessageDTO;
import com.prancibot.chatserver.dto.MessageDTO;
import com.prancibot.chatserver.enums.ChatMessageRole;
import com.prancibot.chatserver.model.ChatMessage;
import com.prancibot.common_ai_rest.model.ChatRole;
import com.prancibot.common_ai_rest.model.LLMChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    ChatMessage toEntity(CreateMessageDTO dto);

    @Mapping(target = "conversationId", source = "conversation.id")
    MessageDTO toDTO(ChatMessage entity);

    @Mapping(target = "role", source = "role")
    LLMChatMessage toLLMChatMessage(ChatDTO dto);

    ChatRole toChatRole(ChatMessageRole role);
}

