package com.example.microchatnotificationservice.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationEventDto(
        UUID eventId,
        Long senderId,
        Long receiverId,
        String type,
        String content,
        LocalDateTime timestamp
) {}
