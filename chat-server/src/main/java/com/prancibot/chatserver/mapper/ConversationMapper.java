package com.prancibot.chatserver.mapper;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.model.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ConversationMapper {
    @Mapping(target = "id", ignore = true)
    Conversation toConversation(CreateConversationDTO dto);

    ConversationDTO toDTO(Conversation entity);

    List<ConversationDTO> toConversationDTOList(List<Conversation> list);
}
