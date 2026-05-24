package com.prancibot.chatserver.kafka.topic;

import com.prancibot.chatserver.configuration.KafkaInternalProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaTopicInitiator {
    @Bean
    public NewTopic conversationMessageTopic(KafkaInternalProperties properties) {
        return TopicBuilder.name(properties.conversationMessagesTopic())
                .partitions(3)
                .replicas(2)
                .build();
    }
}
