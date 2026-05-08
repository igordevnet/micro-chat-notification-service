package com.example.microchatnotificationservice.infrastructure.config.rabbitmq;

import com.example.microchatnotificationservice.application.usecases.NotificationUseCase;
import com.example.microchatnotificationservice.controller.dto.NotificationEventDto;
import com.example.microchatnotificationservice.infrastructure.persistence.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@RabbitListener(queues = "notification.events.queue")
public class ChatListener {

    private final NotificationUseCase notificationUseCase;
    private final NotificationMapper notificationMapper;

    @RabbitHandler
    public void handleDomainEvent(NotificationEventDto event) {
        var notification = notificationMapper.eventToDomain(event);
        notificationUseCase.saveNotification(notification);
    }

    @RabbitHandler(isDefault = true)
    public void onDefault(Object object) {
        log.debug("Unknown event: {}", object);
    }
}
