package com.example.microchatnotificationservice.infrastructure.config.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@RabbitListener(queues = "notification.events.queue")
public class ChatListener {

    @RabbitHandler
    public void handleDomainEvent(DomainEvent event) {

    }

    @RabbitHandler(isDefault = true)
    public void onDefault(Object object) {
        log.debug("Unknown event: {}", object);
    }
}
