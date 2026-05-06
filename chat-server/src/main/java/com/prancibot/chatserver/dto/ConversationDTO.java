package com.prancibot.chatserver.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ConversationDTO {
    private UUID id;
    private String name;
}
