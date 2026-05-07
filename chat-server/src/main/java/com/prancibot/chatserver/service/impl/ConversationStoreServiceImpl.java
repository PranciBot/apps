package com.prancibot.chatserver.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.mapper.ConversationMapper;
import com.prancibot.chatserver.model.Conversation;
import com.prancibot.chatserver.pagination.PaginationParam;
import com.prancibot.chatserver.repository.ConversationRepository;
import com.prancibot.chatserver.service.ConversationStoreService;
import com.prancibot.common.logging.AppLogger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
@Transactional
public class ConversationStoreServiceImpl implements ConversationStoreService {
    private final ConversationMapper mapper;
    private final ConversationRepository repository;
    private final AppLogger logger = AppLogger.getLogger(getClass());

    public ConversationStoreServiceImpl(ConversationMapper mapper, ConversationRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ConversationDTO create(CreateConversationDTO dto) {
        long start = System.currentTimeMillis();
        Conversation conversation = mapper.toConversation(dto);
        repository.save(conversation);
        logger.info("Successfully created conversation: \"{}\" in {} ms",
                conversation.getName(),
                System.currentTimeMillis() - start);

        return mapper.toDTO(conversation);
    }

    @Override
    public ConversationDTO update(UUID id, CreateConversationDTO dto) {
        Conversation conversation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conversation not found: " + id));
        conversation.setName(dto.getName());
        return mapper.toDTO(conversation);
    }

    @Override
    public void delete(UUID id) {
        Conversation conversation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conversation not found: " + id));
        repository.deleteById(conversation.getId());
    }

    @Override
    public List<ConversationDTO> getAllConversation(PaginationParam param) {
        return repository.findAll(param.getLimit(), param.getOffset())
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<ConversationDTO> searchByName(String name, PaginationParam param) {
        long start = System.currentTimeMillis();
        Map<String, String> searchByNameCondition = Map.of("name", name);
        List<ConversationDTO> conversationDTOS = repository
                .findByConditions(searchByNameCondition, param.getLimit(), param.getOffset())
                .stream().map(mapper::toDTO).toList();
        if (AppLogger.isDebugEnabled()) {
            logger.debug("Successfully retrieved {} conversation match conditions {} in {} ms",
                    conversationDTOS.size(), searchByNameCondition, System.currentTimeMillis() - start);
        }
        return conversationDTOS;
    }
}
