package com.prancibot.chatserver.repository;

import java.util.List;
import java.util.UUID;

import com.prancibot.chatserver.model.Conversation;
import com.prancibot.chatserver.pagination.PaginationParam;

public interface ConversationRepository extends Repository<Conversation, UUID> {
    List<Conversation> findByName(String name, PaginationParam param);
}
