package com.prancibot.chatserver.kafka.factory;

import com.prancibot.chatserver.kafka.model.ConversationMessageEvent;
import org.apache.kafka.common.serialization.Deserializer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;
import java.util.function.Supplier;

public class ConversationMessageConsumerFactory extends DefaultKafkaConsumerFactory<@NonNull String, @NonNull ConversationMessageEvent> {
    public ConversationMessageConsumerFactory(Map<String, Object> configs) {
        super(configs);
    }

    public ConversationMessageConsumerFactory(Map<String, Object> configs, @Nullable Deserializer<String> keyDeserializer, @Nullable Deserializer<ConversationMessageEvent> valueDeserializer) {
        super(configs, keyDeserializer, valueDeserializer);
    }

    public ConversationMessageConsumerFactory(Map<String, Object> configs, @Nullable Deserializer<String> keyDeserializer, @Nullable Deserializer<ConversationMessageEvent> valueDeserializer, boolean configureDeserializers) {
        super(configs, keyDeserializer, valueDeserializer, configureDeserializers);
    }

    public ConversationMessageConsumerFactory(Map<String, Object> configs, @Nullable Supplier<@Nullable Deserializer<String>> keyDeserializerSupplier, @Nullable Supplier<@Nullable Deserializer<ConversationMessageEvent>> valueDeserializerSupplier) {
        super(configs, keyDeserializerSupplier, valueDeserializerSupplier);
    }

    public ConversationMessageConsumerFactory(Map<String, Object> configs, @Nullable Supplier<@Nullable Deserializer<String>> keyDeserializerSupplier, @Nullable Supplier<@Nullable Deserializer<ConversationMessageEvent>> valueDeserializerSupplier, boolean configureDeserializers) {
        super(configs, keyDeserializerSupplier, valueDeserializerSupplier, configureDeserializers);
    }
}
