package com.prancibot.chatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConfigurationPropertiesScan
public class ChatServerApplication {
    static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }
}
