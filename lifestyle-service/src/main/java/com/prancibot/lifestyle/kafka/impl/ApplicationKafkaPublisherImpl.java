package com.prancibot.lifestyle.kafka.impl;

import com.prancibot.lifestyle.kafka.ApplicationKafkaPublisher;
import com.prancibot.lifestyle.kafka.KafkaConfig;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@ApplicationScoped
public class ApplicationKafkaPublisherImpl implements ApplicationKafkaPublisher {
    private final KafkaConfig config;
    private KafkaProducer<String, String> producer;

    public ApplicationKafkaPublisherImpl(KafkaConfig config) {
        this.config = config;
    }

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", config.getBootStrapServer());
        props.put("retries", config.getRetries());
        props.put("enable.idempotence", config.isEnableIdempotence());
        props.put("compression.type", config.getCompressionType());
        props.put("linger.ms", config.getLinger());
        props.put("acks", config.getAcks());
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        producer = new KafkaProducer<>(props);
    }

    public void sendMessage(String topic, String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record);
    }

    public void sendMessage(String topic, String key, String value, Callback callback) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record, callback);
    }
}
