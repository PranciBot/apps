package com.prancibot.chatserver.callback.impl;

import com.prancibot.chatserver.kafka.model.ConversationMessageEvent;
import com.prancibot.common.functions.Callback;
import com.prancibot.common.logging.AppLogger;
import org.springframework.stereotype.Component;

@Component
public class DebugCallback implements Callback<ConversationMessageEvent> {
    private final AppLogger logger = AppLogger.getLogger(getClass());

    @Override
    public void execute(ConversationMessageEvent message) {
        logger.info("DebugCallback executed for message: {}", message);
    }
}
