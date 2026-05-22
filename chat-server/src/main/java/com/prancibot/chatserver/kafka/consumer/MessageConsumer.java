package com.prancibot.chatserver.kafka.consumer;

import com.prancibot.common.logging.AppLogger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    private final AppLogger logger = AppLogger.getLogger(getClass());

    @KafkaListener(topics = "${KAFKA_TOPIC_ID}")
    public void consume(ConsumerRecord<String, String> record) {
        logger.info(
                "Consumed Kafka message from topic '{}', partition {}, offset {}, key '{}': {}",
                record.topic(),
                record.partition(),
                record.offset(),
                record.key(),
                record.value()
        );
    }
}
