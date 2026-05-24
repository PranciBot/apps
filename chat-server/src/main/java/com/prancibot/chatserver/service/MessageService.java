package com.prancibot.chatserver.service;

import com.prancibot.chatserver.dto.CreateMessageDTO;
import com.prancibot.chatserver.dto.MessageDTO;
import com.prancibot.chatserver.dto.UpdateMessageDTO;
import com.prancibot.chatserver.pagination.PaginationParam;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageDTO create(UUID conversationId, CreateMessageDTO dto);

    MessageDTO update(UUID messageId, UpdateMessageDTO dto);

    void delete(UUID messageId);

    MessageDTO getById(UUID messageId);

    List<MessageDTO> listByConversation(UUID conversationId, PaginationParam param);
}
