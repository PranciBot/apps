package com.prancibot.chatserver.kafka.topic;

import com.prancibot.chatserver.configuration.KafkaProperties;
import com.prancibot.common.logging.AppLogger;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicInitiator {
    private final KafkaProperties properties;
    private final AppLogger logger = AppLogger.getLogger(getClass());

    public KafkaTopicInitiator(KafkaProperties properties) {
        this.properties = properties;
        init();
    }

    public void init() {
        TopicBuilder.name(properties.AI_RESPONSE_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();

        logger.info("Kafka topic '{}' initialized", properties.AI_RESPONSE_TOPIC);
    }
}
