package com.prancibot.chatserver.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka.internal")
public record KafkaInternalProperties(
        String conversationMessagesTopic
) {
}
