package com.prancibot.chatserver.controller;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.pagination.PaginationParam;
import com.prancibot.chatserver.service.ConversationStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/conversations")
@Tag(name = "Conversations")
public class ConversationController {
    private final ConversationStoreService service;

    public ConversationController(ConversationStoreService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create conversation")
    public ResponseEntity<@NonNull Void> createConversation(
            @RequestBody CreateConversationDTO dto
    ) {
        ConversationDTO conversation = service.create(dto);
        return ResponseEntity
                .created(URI.create("/v1/conversations/" + conversation.getId()))
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update conversation")
    public ResponseEntity<@NonNull ConversationDTO> updateConversation(
            @PathVariable UUID id,
            @RequestBody CreateConversationDTO dto
    ) {
        ConversationDTO conversation = service.update(id, dto);
        return ResponseEntity.ok(conversation);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete conversation")
    public ResponseEntity<@NonNull Void> deleteConversation(
            @PathVariable UUID id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search conversations")
    public ResponseEntity<@NonNull List<ConversationDTO>> searchConversation(
            @RequestParam(required = false) String name,
            PaginationParam pagination
    ) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.ok(List.of());
        }
        List<ConversationDTO> match =
                service.searchByName(name, pagination);

        return ResponseEntity.ok(match);
    }

    @GetMapping
    @Operation(summary = "Get all conversations")
    public ResponseEntity<@NonNull List<ConversationDTO>> getAllConversation(
            PaginationParam pagination
    ) {
        List<ConversationDTO> match =
                service.getAllConversation(pagination);

        return ResponseEntity.ok(match);
    }
}