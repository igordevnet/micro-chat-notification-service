package com.example.microchatnotificationservice.controller.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        Long senderId,
        Long receiverId,
        UUID chatID,
        String type,
        Boolean read,
        String content,
        LocalDateTime timestamp
) {
}
