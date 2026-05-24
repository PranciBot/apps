package com.prancibot.chatserver.service.impl;

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
import com.prancibot.chatserver.utils.ExceptionFactory;
import com.prancibot.common.logging.AppLogger;
import com.prancibot.common.monitoring.annotation.Timed;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final MessageMapper mapper;
    private final AppLogger logger = AppLogger.getLogger(getClass());

    public MessageServiceImpl(
            MessageRepository messageRepository,
            ConversationRepository conversationRepository,
            MessageMapper mapper
    ) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.mapper = mapper;
    }

    @Override
    @Timed
    public MessageDTO create(UUID conversationId, CreateMessageDTO dto) {
        logger.info("Creating message for conversation: {}", conversationId);
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> ExceptionFactory.entityNotFoundException("Conversation", conversationId));

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
                .orElseThrow(() -> ExceptionFactory.entityNotFoundException("Message", messageId));

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
                .orElseThrow(() -> ExceptionFactory.entityNotFoundException("Message", messageId));
        messageRepository.deleteById(message.getId());
        logger.info("Deleted message: {}", messageId);
    }

    @Override
    @Timed
    public MessageDTO getById(UUID messageId) {
        ChatMessage message = messageRepository.findById(messageId)
                .orElseThrow(() -> ExceptionFactory.entityNotFoundException("Message", messageId));
        return mapper.toDTO(message);
    }

    @Override
    @Timed
    public List<MessageDTO> listByConversation(UUID conversationId, PaginationParam param) {
        if (!conversationRepository.existsById(conversationId)) {
            throw ExceptionFactory.entityNotFoundException("Conversation", conversationId);
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
}
