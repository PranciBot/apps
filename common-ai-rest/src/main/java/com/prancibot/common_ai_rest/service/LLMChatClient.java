package com.prancibot.common_ai_rest.service;

import com.prancibot.common_ai_rest.model.LLMChatRequest;
import com.prancibot.common_ai_rest.model.LLMChatResponse;

import java.util.stream.Stream;

public interface LLMChatClient {
    LLMChatResponse chat(LLMChatRequest request);

    Stream<String> streamChat(LLMChatRequest request);

    default String chat(String model, String prompt) {
        return chat(LLMChatRequest.userPrompt(model, prompt)).content();
    }

    default Stream<String> streamChat(String model, String prompt) {
        return streamChat(LLMChatRequest.userPrompt(model, prompt));
    }
}
