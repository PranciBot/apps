package com.prancibot.chatserver.kafka.factory;

import com.prancibot.chatserver.kafka.model.ConversationMessageEvent;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
public class KafkaContainerFactory {
    @Bean
    ConcurrentKafkaListenerContainerFactory<@NonNull String, @NonNull ConversationMessageEvent> manualAckFactory(
            ConversationMessageConsumerFactory consumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<@NonNull String, @NonNull ConversationMessageEvent>();
        factory.setConsumerFactory(consumerFactory);

        factory.getContainerProperties()
                .setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }
}
