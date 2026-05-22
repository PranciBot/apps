package com.prancibot.chatserver.dto;

import com.prancibot.chatserver.enums.ChatMessageRole;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class MessageDTO {
    private UUID id;
    private UUID conversationId;
    private ChatMessageRole role;
    private String content;
    private Instant creationDate;
}

