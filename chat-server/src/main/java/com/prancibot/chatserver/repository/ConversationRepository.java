package com.prancibot.chatserver.repository;

import com.prancibot.chatserver.model.Conversation;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<@NonNull Conversation, @NonNull UUID> {
    List<Conversation> findByName(String name, Pageable pageable);
}
