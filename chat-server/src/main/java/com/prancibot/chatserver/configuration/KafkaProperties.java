package com.prancibot.chatserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaProperties {
    @Value("${KAFKA_TOPIC_ID}")
    public String AI_MESSAGE_TOPIC;
}
