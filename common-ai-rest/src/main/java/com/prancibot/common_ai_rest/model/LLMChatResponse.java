package com.prancibot.common_ai_rest.model;

public record LLMChatResponse(
        String id,
        String model,
        String content,
        String finishReason
) {
}
