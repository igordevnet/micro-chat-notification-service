package com.example.microchatnotificationservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    private UUID id;

    private Long senderId;

    private Long receiverId;

    private UUID chatId;

    private String type;

    private String content;

    private Boolean read;

    private LocalDateTime timestamp;
}
