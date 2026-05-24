package com.prancibot.chatserver.service.impl;

import com.prancibot.chatserver.configuration.AIServiceConfig;
import com.prancibot.chatserver.dto.ChatDTO;
import com.prancibot.chatserver.kafka.model.ConversationMessageEvent;
import com.prancibot.chatserver.mapper.MessageMapper;
import com.prancibot.chatserver.repository.ConversationRepository;
import com.prancibot.chatserver.service.StreamingChatService;
import com.prancibot.chatserver.utils.ExceptionFactory;
import com.prancibot.common.functions.Callback;
import com.prancibot.common.logging.AppLogger;
import com.prancibot.common.monitoring.annotation.Timed;
import com.prancibot.common_ai_rest.model.LLMChatRequest;
import com.prancibot.common_ai_rest.service.LLMChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class StreamingChatServiceImpl implements StreamingChatService {
    private final AIServiceConfig aiServiceConfig;
    private final LLMChatClient llmChatClient;
    private final List<Callback<ConversationMessageEvent>> postProcessingCallbacks;
    private final ConversationRepository conversationRepository;
    private final MessageMapper mapper;
    private final AppLogger logger = AppLogger.getLogger(getClass());

    public StreamingChatServiceImpl(AIServiceConfig aiServiceConfig,
                                    LLMChatClient llmChatClient,
                                    List<Callback<ConversationMessageEvent>> postProcessingCallbacks,
                                    ConversationRepository conversationRepository,
                                    MessageMapper mapper) {
        this.aiServiceConfig = aiServiceConfig;
        this.llmChatClient = llmChatClient;
        this.postProcessingCallbacks = postProcessingCallbacks;
        this.conversationRepository = conversationRepository;
        this.mapper = mapper;
    }

    @Override
    @Timed
    public Stream<String> chat(UUID conversationId, List<ChatDTO> messages) {
        if (!conversationRepository.existsById(conversationId)) {
            throw ExceptionFactory.entityNotFoundException("Conversation", conversationId);
        }

        LLMChatRequest request = new LLMChatRequest(
                aiServiceConfig.MODEL_NAME,
                messages.stream()
                        .map(mapper::toLLMChatMessage)
                        .toList()
        );

        return llmChatClient.streamChat(request);
    }

    /**
     * This method is called after the streaming chat is done. It executes all registered post-processing callbacks with the final message.
     *
     * @param message the message that was processed after streaming chat is done
     */
    @Timed
    public void onStreamingChatDone(ConversationMessageEvent message) {
        logger.debug("Executing post-processing callbacks for message: {}", message);
        postProcessingCallbacks.forEach(callback -> callback.execute(message));
    }
}
