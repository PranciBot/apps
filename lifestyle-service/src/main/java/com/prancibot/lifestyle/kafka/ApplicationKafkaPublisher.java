package com.prancibot.lifestyle.kafka;

import org.apache.kafka.clients.producer.Callback;

public interface ApplicationKafkaPublisher {
    void sendMessage(String topic, String key, String value);

    void sendMessage(String topic, String key, String value, Callback callback);
}
