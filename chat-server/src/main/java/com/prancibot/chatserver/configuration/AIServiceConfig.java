package com.prancibot.chatserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AIServiceConfig {
    @Value("${OPENAI_API_KEY}")
    public String OPENAI_API_KEY;

    @Value("${MODEL_NAME}")
    public String MODEL_NAME;

    @Value("${OPENAI_BASE_URL}")
    public String OPENAI_BASE_URL;
}
