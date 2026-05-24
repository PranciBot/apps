package com.prancibot.chatserver.service;

import com.prancibot.chatserver.dto.ChatDTO;
import com.prancibot.chatserver.kafka.model.ConversationMessageEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface StreamingChatService {
    Stream<String> chat(UUID conversationId, List<ChatDTO> messages);

    void onStreamingChatDone(ConversationMessageEvent message);
}
