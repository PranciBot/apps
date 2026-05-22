package com.prancibot.chatserver.controller;

import com.prancibot.chatserver.dto.ChatDTO;
import com.prancibot.chatserver.dto.CreateMessageDTO;
import com.prancibot.chatserver.dto.MessageDTO;
import com.prancibot.chatserver.dto.UpdateMessageDTO;
import com.prancibot.chatserver.pagination.PaginationParam;
import com.prancibot.chatserver.service.MessageService;
import com.prancibot.common.RequestHeaders;
import com.prancibot.common.logging.AppLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1")
@Tag(name = "Messages")
public class MessageController {
    private final MessageService service;
    private final AppLogger logger = AppLogger.getLogger(getClass());

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping("/conversations/{conversationId}/messages")
    @Operation(summary = "Create message for conversation")
    public ResponseEntity<@NonNull Void> createMessage(
            @PathVariable UUID conversationId,
            @RequestBody CreateMessageDTO dto
    ) {
        MessageDTO message = service.create(conversationId, dto);
        return ResponseEntity
                .created(URI.create("/v1/messages/" + message.getId()))
                .build();
    }

    @PostMapping(
            value = "/conversations/{conversationId}/chat",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    @Operation(summary = "Chat with conversation")
    public StreamingResponseBody streamMessages(
            @PathVariable("conversationId") UUID conversationId,
            @RequestBody List<ChatDTO> dtoList,
            HttpServletResponse response
    ) {
        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader(RequestHeaders.ACCEL_BUFFERING, "no");
        return outputStream -> {
            StringBuilder buffer = new StringBuilder();
            try (Stream<String> stream = service.chat(conversationId, dtoList)) {
                stream.forEach(chunk -> {
                    try {
                        buffer.append(chunk);
                        outputStream.write(formatSSEData(chunk).getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to write chat chunk to output stream", e);
                    }
                });
            }
            logger.info("Message: {}", buffer.toString());
            logger.info("Completed chat stream for conversation: {}, total response length: {}", conversationId, buffer.length());
        };
    }

    @GetMapping("/conversations/{conversationId}/messages")
    @Operation(summary = "List messages for conversation")
    public ResponseEntity<@NonNull List<MessageDTO>> listMessages(
            @PathVariable("conversationId") UUID conversationId,
            PaginationParam pagination
    ) {
        return ResponseEntity.ok(service.listByConversation(conversationId, pagination));
    }

    @GetMapping("/messages/{id}")
    @Operation(
            summary = "Get message by id",
            description = "Get specific message details by id"
    )
    public ResponseEntity<@NonNull MessageDTO> getMessage(
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/messages/{id}")
    @Operation(summary = "Update message")
    public ResponseEntity<@NonNull MessageDTO> updateMessage(
            @PathVariable("id") UUID id,
            @RequestBody UpdateMessageDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/messages/{id}")
    @Operation(summary = "Delete message")
    public ResponseEntity<@NonNull Void> deleteMessage(
            @PathVariable("id") UUID id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private String formatSSEData(String data) {
        return "data: " + data.replace("\n", "\ndata: ") + "\n\n";
    }
}

