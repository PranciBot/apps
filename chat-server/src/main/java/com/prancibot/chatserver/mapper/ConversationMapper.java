package com.prancibot.chatserver.mapper;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.model.Conversation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
    Conversation toConversation(CreateConversationDTO dto);

    ConversationDTO toDTO(Conversation entity);

    List<ConversationDTO> toConversationDTOList(List<Conversation> list);
}
