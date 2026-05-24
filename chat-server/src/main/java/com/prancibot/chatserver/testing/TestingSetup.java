package com.prancibot.chatserver.testing;

import com.prancibot.chatserver.configuration.KafkaInternalProperties;
import com.prancibot.common.logging.AppLogger;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.List;

@Configuration
public class TestingSetup {

    private final AppLogger logger = AppLogger.getLogger(getClass());

    @Bean
    @Profile("dev")
    ApplicationRunner resetTopics(
            KafkaAdmin kafkaAdmin,
            KafkaInternalProperties properties
    ) {
        return args -> {
            try (AdminClient adminClient =
                         AdminClient.create(kafkaAdmin.getConfigurationProperties())) {

                adminClient.deleteTopics(
                        List.of(properties.conversationMessagesTopic())
                ).all().get();

                logger.info("Deleted Kafka topics for testing setup in 'dev' profile");
            } catch (Exception e) {
                logger.error("Failed to delete Kafka topics for testing setup in 'dev' profile", e);
            }
        };
    }
}
