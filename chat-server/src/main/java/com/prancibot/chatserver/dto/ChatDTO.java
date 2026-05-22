package com.prancibot.chatserver.dto;

import com.prancibot.chatserver.enums.ChatMessageRole;
import lombok.Data;

@Data
public class ChatDTO {
    private ChatMessageRole role;
    private String content;
}
