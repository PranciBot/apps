package com.prancibot.chatserver.service;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.pagination.PaginationParam;

import java.util.List;

public interface ConversationStoreService {
    ConversationDTO create(CreateConversationDTO dto);

    List<ConversationDTO> getAllConversation(PaginationParam param);

    List<ConversationDTO> searchByName(String name, PaginationParam param);
}
