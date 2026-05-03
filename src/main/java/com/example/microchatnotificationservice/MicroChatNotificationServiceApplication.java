package com.example.microchatnotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MicroChatNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroChatNotificationServiceApplication.class, args);
    }

}
