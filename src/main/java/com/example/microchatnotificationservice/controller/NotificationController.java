package com.example.microchatnotificationservice.controller;

import com.example.microchatnotificationservice.application.usecases.NotificationUseCase;
import com.example.microchatnotificationservice.controller.dto.response.NotificationPaginatedResponse;
import com.example.microchatnotificationservice.controller.dto.response.NotificationResponse;
import com.example.microchatnotificationservice.infrastructure.config.UserAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationUseCase notificationUseCase;

    @GetMapping
    public ResponseEntity<NotificationPaginatedResponse> getUserNotifications(
            @AuthenticationPrincipal UserAuthenticated currentUser,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(notificationUseCase.getRecentNotifications(currentUser.id(), page, size));
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable UUID notificationId,
            @AuthenticationPrincipal UserAuthenticated currentUser
    ) {
        notificationUseCase.markAsRead(notificationId, currentUser.id());
        return ResponseEntity.noContent().build();
    }
}
