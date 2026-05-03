package com.example.microchatnotificationservice.controller.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record NotificationPaginatedResponse(
        List<NotificationResponse> content,
        int currentPage,
        int totalPages,
        long totalElements
){
}
