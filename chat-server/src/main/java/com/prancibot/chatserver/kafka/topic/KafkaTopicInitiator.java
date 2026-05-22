package com.prancibot.chatserver.kafka.topic;

import com.prancibot.chatserver.configuration.KafkaProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaTopicInitiator {
    @Bean
    public NewTopic aiResponseTopic(KafkaProperties properties) {
        return TopicBuilder.name(properties.AI_MESSAGE_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
