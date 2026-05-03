package com.example.microchatnotificationservice.infrastructure.gateways;

import com.example.microchatnotificationservice.application.gateways.MessageBrokerGateway;
import com.example.microchatnotificationservice.controller.dto.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqBrokerGateway implements MessageBrokerGateway {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void convertAndSend(String exchange, String routingKey, NotificationResponse response) {
        rabbitTemplate.convertAndSend(exchange, routingKey, response);
    }
}
