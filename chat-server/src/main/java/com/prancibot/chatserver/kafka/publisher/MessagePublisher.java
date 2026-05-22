package com.prancibot.chatserver.kafka.publisher;

import com.prancibot.chatserver.configuration.KafkaProperties;
import com.prancibot.common.logging.AppLogger;
import org.jspecify.annotations.NonNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {
    private final KafkaTemplate<@NonNull String, @NonNull String> kafkaTemplate;
    private final KafkaProperties properties;
    private final AppLogger logger = AppLogger.getLogger(getClass());

    public MessagePublisher(KafkaTemplate<@NonNull String, @NonNull String> kafkaTemplate, KafkaProperties properties) {
        this.kafkaTemplate = kafkaTemplate;
        this.properties = properties;
    }

    public void publish(String key, String message) {
        if (key == null) {
            key = "";
        }

        kafkaTemplate.send(properties.AI_MESSAGE_TOPIC, key, message)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        logger.error("Failed to publish Kafka message to topic '{}'", exception, properties.AI_MESSAGE_TOPIC);
                        return;
                    }

                    logger.info(
                            "Published Kafka message to topic '{}', partition {}, offset {}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset()
                    );
                });
    }
}
