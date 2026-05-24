package com.prancibot.chatserver.kafka.model;

import com.prancibot.chatserver.enums.ChatMessageRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationMessageEvent {
    private UUID conversationId;
    private ChatMessageRole role;
    private String content;
}
