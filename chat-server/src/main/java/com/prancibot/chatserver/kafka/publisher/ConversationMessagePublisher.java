package com.prancibot.chatserver.kafka.publisher;

import com.prancibot.chatserver.configuration.KafkaInternalProperties;
import com.prancibot.chatserver.kafka.messageTemplates.Templates;
import com.prancibot.chatserver.kafka.model.ConversationMessageEvent;
import com.prancibot.common.logging.AppLogger;
import org.jspecify.annotations.NonNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConversationMessagePublisher {
    private final KafkaTemplate<@NonNull String, @NonNull ConversationMessageEvent> kafkaTemplate;
    private final KafkaInternalProperties properties;
    private final AppLogger logger = AppLogger.getLogger(getClass());

    public ConversationMessagePublisher(KafkaTemplate<@NonNull String, @NonNull ConversationMessageEvent> kafkaTemplate, KafkaInternalProperties properties) {
        this.kafkaTemplate = kafkaTemplate;
        this.properties = properties;
    }

    public void publishMessage(ConversationMessageEvent message) {
        Objects.requireNonNull(message.getRole().toString(), "Message key cannot be null");
        Objects.requireNonNull(message, "Message cannot be null");

        kafkaTemplate.send(properties.conversationMessagesTopic(), message.getRole().toString(), message)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        logger.error(Templates.FailedToPublishMessageTemplate, exception, properties.conversationMessagesTopic());
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
