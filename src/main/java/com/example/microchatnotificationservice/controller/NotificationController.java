package com.example.microchatnotificationservice.controller;

import com.example.microchatnotificationservice.application.usecases.NotificationUseCase;
import com.example.microchatnotificationservice.controller.dto.response.NotificationResponse;
import com.example.microchatnotificationservice.infrastructure.config.UserAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationUseCase notificationUseCase;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(
            @AuthenticationPrincipal UserAuthenticated currentUser
    ) {
        List<NotificationResponse> history = notificationUseCase.getRecentNotifications(currentUser.id());
        return ResponseEntity.ok(history);
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable String notificationId,
            @AuthenticationPrincipal UserAuthenticated currentUser
    ) {
        notificationUseCase.markAsRead(notificationId, currentUser.id());
        return ResponseEntity.noContent().build();
    }
}
