package com.prancibot.common_ai_rest.model;

import java.util.Objects;

public record LLMChatMessage(ChatRole role, String content) {
    public LLMChatMessage {
        Objects.requireNonNull(role, "role is required");
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content is required");
        }
    }

    public static LLMChatMessage system(String content) {
        return new LLMChatMessage(ChatRole.SYSTEM, content);
    }

    public static LLMChatMessage user(String content) {
        return new LLMChatMessage(ChatRole.USER, content);
    }

    public static LLMChatMessage assistant(String content) {
        return new LLMChatMessage(ChatRole.ASSISTANT, content);
    }
}
