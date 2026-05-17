package com.example.microchatnotificationservice.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationEventDto(
        @NotNull Long senderId,
        @NotNull Long receiverId,
        @NotNull UUID chatId,
        @NotNull String type,
        @NotNull String content,
        @NotNull LocalDateTime timestamp
) {}
