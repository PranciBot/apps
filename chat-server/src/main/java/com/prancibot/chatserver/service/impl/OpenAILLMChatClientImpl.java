package com.prancibot.chatserver.service.impl;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.http.StreamResponse;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionChunk;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.prancibot.common_ai_rest.config.AIProviderConfig;
import com.prancibot.common_ai_rest.model.LLMChatMessage;
import com.prancibot.common_ai_rest.model.LLMChatRequest;
import com.prancibot.common_ai_rest.model.LLMChatResponse;
import com.prancibot.common_ai_rest.service.LLMChatClient;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class OpenAILLMChatClientImpl implements LLMChatClient, AutoCloseable {
    private final OpenAIClient client;

    public OpenAILLMChatClientImpl() {
        this(OpenAIOkHttpClient.fromEnv());
    }

    public OpenAILLMChatClientImpl(AIProviderConfig config) {
        this(buildClient(config));
    }

    public OpenAILLMChatClientImpl(OpenAIClient client) {
        this.client = Objects.requireNonNull(client, "client is required");
    }

    private static OpenAIClient buildClient(AIProviderConfig config) {
        Objects.requireNonNull(config, "config is required");

        OpenAIOkHttpClient.Builder builder = OpenAIOkHttpClient.builder().fromEnv();
        if (hasText(config.getApiKey())) {
            builder.apiKey(config.getApiKey());
        }
        if (hasText(config.getApiUrl())) {
            builder.baseUrl(config.getApiUrl());
        }
        return builder.build();
    }

    private static void addMessageToBuilder(ChatCompletionCreateParams.Builder builder, LLMChatMessage message) {
        switch (message.role()) {
            case SYSTEM -> builder.addSystemMessage(message.content());
            case USER -> builder.addUserMessage(message.content());
            case ASSISTANT -> builder.addAssistantMessage(message.content());
        }
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private static ChatCompletionCreateParams createParams(LLMChatRequest request) {
        ChatCompletionCreateParams.Builder paramsBuilder = ChatCompletionCreateParams.builder()
                .model(request.model());

        for (LLMChatMessage message : request.messages()) {
            addMessageToBuilder(paramsBuilder, message);
        }
        if (request.temperature() != null) {
            paramsBuilder.temperature(request.temperature());
        }
        if (request.maxCompletionTokens() != null) {
            paramsBuilder.maxCompletionTokens(request.maxCompletionTokens());
        }

        return paramsBuilder.build();
    }

    @Override
    public LLMChatResponse chat(LLMChatRequest request) {
        Objects.requireNonNull(request, "request is required");

        ChatCompletion completion = client.chat().completions().create(createParams(request));
        if (completion.choices().isEmpty()) {
            return new LLMChatResponse(completion.id(), completion.model(), "", null);
        }

        ChatCompletion.Choice firstChoice = completion.choices().getFirst();
        String content = firstChoice.message().content().orElse("");
        return new LLMChatResponse(
                completion.id(),
                completion.model(),
                content,
                firstChoice.finishReason().asString()
        );
    }

    @Override
    public Stream<String> streamChat(LLMChatRequest request) {
        Objects.requireNonNull(request, "request is required");

        StreamResponse<ChatCompletionChunk> streamResponse =
                client.chat().completions().createStreaming(createParams(request));

        return streamResponse.stream()
                .flatMap(chunk -> chunk.choices().stream())
                .map(ChatCompletionChunk.Choice::delta)
                .flatMap(delta -> delta.content().stream())
                .filter(content -> !content.isEmpty())
                .onClose(streamResponse::close);
    }

    @Override
    public void close() {
        client.close();
    }
}
