package com.prancibot.chatserver.service.impl;

import com.prancibot.chatserver.configuration.AIServiceConfig;
import com.prancibot.chatserver.dto.ChatDTO;
import com.prancibot.chatserver.dto.CreateMessageDTO;
import com.prancibot.chatserver.dto.MessageDTO;
import com.prancibot.chatserver.dto.UpdateMessageDTO;
import com.prancibot.chatserver.mapper.MessageMapper;
import com.prancibot.chatserver.model.ChatMessage;
import com.prancibot.chatserver.model.Conversation;
import com.prancibot.chatserver.pagination.PaginationParam;
import com.prancibot.chatserver.repository.ConversationRepository;
import com.prancibot.chatserver.repository.MessageRepository;
import com.prancibot.chatserver.service.MessageService;
import com.prancibot.common.exception.EntityNotFoundException;
import com.prancibot.common.logging.AppLogger;
import com.prancibot.common.monitoring.annotation.Timed;
import com.prancibot.common_ai_rest.model.LLMChatRequest;
import com.prancibot.common_ai_rest.service.LLMChatClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final MessageMapper mapper;
    private final AIServiceConfig aiServiceConfig;
    private final AppLogger logger = AppLogger.getLogger(getClass());
    private final LLMChatClient llmChatClient;

    public MessageServiceImpl(
            MessageRepository messageRepository,
            ConversationRepository conversationRepository,
            MessageMapper mapper,
            AIServiceConfig aiServiceConfig,
            LLMChatClient llmChatClient
    ) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.mapper = mapper;
        this.aiServiceConfig = aiServiceConfig;
        this.llmChatClient = llmChatClient;
    }

    @Override
    @Timed
    public Stream<String> chat(UUID conversationId, List<ChatDTO> messages) {
        if (!conversationRepository.existsById(conversationId)) {
            throw messageNotFound("Conversation", conversationId);
        }

        LLMChatRequest request = new LLMChatRequest(
                aiServiceConfig.MODEL_NAME,
                messages.stream()
                        .map(mapper::toLLMChatMessage)
                        .toList()
        );

        return llmChatClient.streamChat(request);
    }

    @Override
    @Timed
    public MessageDTO create(UUID conversationId, CreateMessageDTO dto) {
        logger.info("Creating message for conversation: {}", conversationId);
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> messageNotFound("Conversation", conversationId));

        ChatMessage message = mapper.toEntity(dto);
        message.setConversation(conversation);

        messageRepository.save(message);
        logger.info("Created message with id: {}", message.getId());
        return mapper.toDTO(message);
    }

    @Override
    @Timed
    public MessageDTO update(UUID messageId, UpdateMessageDTO dto) {
        logger.info("Updating message: {}", messageId);
        ChatMessage message = messageRepository.findById(messageId)
                .orElseThrow(() -> messageNotFound("Message", messageId));

        message.setRole(dto.getRole());
        message.setContent(dto.getContent());
        logger.info("Updated message: {}", messageId);
        return mapper.toDTO(message);
    }

    @Override
    @Timed
    public void delete(UUID messageId) {
        logger.info("Deleting message: {}", messageId);
        ChatMessage message = messageRepository.findById(messageId)
                .orElseThrow(() -> messageNotFound("Message", messageId));
        messageRepository.deleteById(message.getId());
        logger.info("Deleted message: {}", messageId);
    }

    @Override
    @Timed
    public MessageDTO getById(UUID messageId) {
        ChatMessage message = messageRepository.findById(messageId)
                .orElseThrow(() -> messageNotFound("Message", messageId));
        return mapper.toDTO(message);
    }

    @Override
    @Timed
    public List<MessageDTO> listByConversation(UUID conversationId, PaginationParam param) {
        if (!conversationRepository.existsById(conversationId)) {
            throw messageNotFound("Conversation", conversationId);
        }
        return messageRepository
                .getByConversationIdOrderByCreationDate(
                        conversationId,
                        PageRequest.of(param.getPage(), param.getSize())
                )
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    private EntityNotFoundException messageNotFound(String type, UUID id) {
        return new EntityNotFoundException(type + " not found: " + id);
    }
}
