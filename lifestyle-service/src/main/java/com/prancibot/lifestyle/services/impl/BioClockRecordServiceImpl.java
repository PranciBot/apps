package com.prancibot.lifestyle.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prancibot.common.kafka.KafkaTopics;
import com.prancibot.lifestyle.dto.WakeSleepRecord;
import com.prancibot.lifestyle.enums.Activity;
import com.prancibot.lifestyle.kafka.ApplicationKafkaPublisher;
import com.prancibot.lifestyle.services.BioClockRecordService;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApplicationScoped
public class BioClockRecordServiceImpl implements BioClockRecordService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final ApplicationKafkaPublisher publisher;
    private final ObjectMapper mapper = new ObjectMapper();

    public BioClockRecordServiceImpl(ApplicationKafkaPublisher publisher) {
        this.publisher = publisher;
    }

    public void record(String activity) {
        String userTimezone = getUserTimezone();
        Activity validActivity = Activity.from(activity);

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(userTimezone));
        Instant instant = zonedDateTime.toInstant();
        WakeSleepRecord response = new WakeSleepRecord(userTimezone, instant.toString(), validActivity);

        try {
            publisher.sendMessage(KafkaTopics.LIFE_STYPE_TRACKING, activity, mapper.writeValueAsString(response));
        } catch (Exception e) {
            LOGGER.info("Something went wrong when record {} activity, {}", activity, e.getMessage());
        }
    }

    private String getUserTimezone() {
        return ZoneId.systemDefault().getId();
    }
}
