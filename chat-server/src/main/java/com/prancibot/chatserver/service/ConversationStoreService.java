package com.prancibot.chatserver.service;

import java.util.List;
import java.util.UUID;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.pagination.PaginationParam;

public interface ConversationStoreService {
    ConversationDTO create(CreateConversationDTO dto);

    ConversationDTO update(UUID id, CreateConversationDTO dto);

    void delete(UUID id);

    List<ConversationDTO> getAllConversation(PaginationParam param);

    List<ConversationDTO> searchByName(String name, PaginationParam param);
}
