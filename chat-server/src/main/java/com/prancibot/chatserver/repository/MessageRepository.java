package com.prancibot.chatserver.repository;

import com.prancibot.chatserver.model.ChatMessage;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<@NonNull ChatMessage, @NonNull UUID> {
    List<ChatMessage> getByConversationIdOrderByCreationDate(UUID conversationId, Pageable pageable);
}
