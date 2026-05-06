package com.example.microchatnotificationservice.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NotificationEventDto(
        @NotNull Long senderId,
        @NotNull Long receiverId,
        @NotNull String type,
        @NotNull String content,
        @NotNull LocalDateTime timestamp
) {}
