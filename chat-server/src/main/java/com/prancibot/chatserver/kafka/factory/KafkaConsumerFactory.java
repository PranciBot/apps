package com.prancibot.chatserver.kafka.factory;

import com.prancibot.chatserver.kafka.model.ConversationMessageEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerFactory {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConversationMessageConsumerFactory conversationMessageConsumerFactory() {
        JacksonJsonDeserializer<@NonNull ConversationMessageEvent> deserializer = new JacksonJsonDeserializer<>(ConversationMessageEvent.class);
        deserializer.addTrustedPackages("com.prancibot.chatserver.kafka.model");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // TODO: better way to handle reset config?
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);

        return new ConversationMessageConsumerFactory(props, new StringDeserializer(), deserializer);
    }
}
