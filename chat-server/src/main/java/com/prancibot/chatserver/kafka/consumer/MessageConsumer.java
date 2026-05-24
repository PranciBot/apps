package com.prancibot.chatserver.kafka.consumer;

import com.prancibot.chatserver.kafka.model.ConversationMessageEvent;
import com.prancibot.common.logging.AppLogger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    private final AppLogger logger = AppLogger.getLogger(getClass());
    private final String GROUP_ID = "message-consumers";

    @KafkaListener(
            topics = "${kafka.internal.conversation-messages-topic}",
            groupId = GROUP_ID,
            containerFactory = "manualAckFactory"
    )
    public void syncMessageToDatabaseConsumer(
            ConsumerRecord<String, ConversationMessageEvent> record,
            Acknowledgment acknowledgment
    ) {
        logger.info(
                "Consumed Kafka message from topic '{}', partition {}, offset {}, key '{}': {}",
                record.topic(),
                record.partition(),
                record.offset(),
                record.key(),
                record.value()
        );

        acknowledgment.acknowledge();
    }
}
