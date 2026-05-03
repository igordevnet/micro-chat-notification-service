package com.example.microchatnotificationservice.application.gateways;

import com.example.microchatnotificationservice.controller.dto.response.NotificationResponse;

public interface MessageBrokerGateway {
    void convertAndSend(String exchange, String routingKey, NotificationResponse response);
}
