package com.prancibot.chatserver.repository;

import com.prancibot.chatserver.model.Conversation;
import com.prancibot.chatserver.pagination.PaginationParam;

import java.util.List;
import java.util.UUID;

public interface ConversationRepository extends Repository<Conversation, UUID> {
    List<Conversation> findByName(String name, PaginationParam param);
}
