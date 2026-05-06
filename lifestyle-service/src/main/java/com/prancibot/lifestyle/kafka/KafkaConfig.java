package com.prancibot.lifestyle.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class KafkaConfig {
    @ConfigProperty(name = "kafka.bootstrap.servers")
    String bootStrapServer;
    @ConfigProperty(name = "kafka.producer.retries")
    int retries;
    @ConfigProperty(name = "kafka.enable.idempotence", defaultValue = "true")
    boolean enableIdempotence;
    @ConfigProperty(name = "kafka.compression.type", defaultValue = "snappy")
    String compressionType;
    @ConfigProperty(name = "kafka.linger.ms", defaultValue = "0")
    int linger;
    @ConfigProperty(name = "kafka.acks", defaultValue = "all")
    String acks;

    public String getAcks() {
        return acks;
    }

    public String getBootStrapServer() {
        return bootStrapServer;
    }

    public int getRetries() {
        return retries;
    }

    public boolean isEnableIdempotence() {
        return enableIdempotence;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public int getLinger() {
        return linger;
    }
}
