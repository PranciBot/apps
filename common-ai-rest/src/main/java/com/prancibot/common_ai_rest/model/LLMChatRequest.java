package com.prancibot.common_ai_rest.model;

import java.util.List;

public record LLMChatRequest(
        String model,
        List<LLMChatMessage> messages,
        Double temperature,
        Long maxCompletionTokens
) {
    public LLMChatRequest {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("model is required");
        }
        if (messages == null || messages.isEmpty()) {
            throw new IllegalArgumentException("at least one message is required");
        }
        messages = List.copyOf(messages);
    }

    public LLMChatRequest(String model, List<LLMChatMessage> messages) {
        this(model, messages, null, null);
    }

    public static LLMChatRequest userPrompt(String model, String prompt) {
        return new LLMChatRequest(model, List.of(LLMChatMessage.user(prompt)));
    }
}
