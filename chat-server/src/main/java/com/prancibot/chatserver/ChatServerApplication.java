package com.prancibot.chatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ChatServerApplication {
    static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }
}
