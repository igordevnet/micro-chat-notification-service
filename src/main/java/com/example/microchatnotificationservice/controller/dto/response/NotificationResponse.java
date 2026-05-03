package com.example.microchatnotificationservice.controller.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
        UUID eventId,
        Long senderId,
        Long receiverId,
        String type,
        String content,
        LocalDateTime timestamp
) {
}
