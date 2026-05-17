package com.prancibot.chatserver.service.impl;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.mapper.ConversationMapper;
import com.prancibot.chatserver.model.Conversation;
import com.prancibot.chatserver.pagination.PaginationParam;
import com.prancibot.chatserver.repository.ConversationRepository;
import com.prancibot.chatserver.service.ConversationStoreService;
import com.prancibot.common.exception.EntityNotFoundException;
import com.prancibot.common.logging.AppLogger;
import com.prancibot.common.monitoring.annotation.Timed;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConversationStoreServiceImpl implements ConversationStoreService {
    private final ConversationMapper mapper;
    private final ConversationRepository repository;
    private final AppLogger logger = AppLogger.getLogger(getClass());

    public ConversationStoreServiceImpl(ConversationMapper mapper, ConversationRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    @Timed
    public ConversationDTO create(CreateConversationDTO dto) {
        logger.info("Creating conversation with name: {}", dto.getName());
        Conversation conversation = mapper.toConversation(dto);
        repository.save(conversation);
        logger.info("Created conversation with id: {}", conversation.getId());
        return mapper.toDTO(conversation);
    }

    @Override
    @Timed
    public ConversationDTO update(
            UUID id,
            CreateConversationDTO dto
    ) {
        logger.info("Updating conversation with id: {}", id);
        Conversation conversation =
                repository.findById(id)
                        .orElseThrow(() ->
                                throwWhenConversationNotFound(id)
                        );
        logger.debug("Old conversation name: {}", conversation.getName());
        conversation.setName(dto.getName());
        logger.info("Updated conversation {} with new name: {}", id, dto.getName());
        return mapper.toDTO(conversation);
    }

    @Override
    @Timed
    public void delete(UUID id) {
        logger.info("Deleting conversation with id: {}", id);
        Conversation conversation =
                repository.findById(id)
                        .orElseThrow(() ->
                                throwWhenConversationNotFound(id)
                        );
        repository.deleteById(conversation.getId());
        logger.info("Deleted conversation with id: {}", id
        );
    }

    @Override
    @Timed
    public List<ConversationDTO> getAllConversation(PaginationParam param) {
        return repository.findAll(PageRequest.of(param.getPage(), param.getSize()))
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    @Timed
    public List<ConversationDTO> searchByName(String name, PaginationParam param) {
        return repository.findByName(name, PageRequest.of(param.getPage(), param.getSize()))
                .stream().map(mapper::toDTO).toList();
    }

    private EntityNotFoundException throwWhenConversationNotFound(UUID id) {
        return new EntityNotFoundException("Conversation not found: " + id);
    }
}
