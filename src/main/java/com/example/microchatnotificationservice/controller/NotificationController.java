package com.example.microchatnotificationservice.controller;

import com.example.microchatnotificationservice.application.usecases.NotificationUseCase;
import com.example.microchatnotificationservice.controller.dto.response.NotificationPaginatedResponse;
import com.example.microchatnotificationservice.infrastructure.config.UserAuthenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Endpoints for managing real-time user notifications")
public class NotificationController {

    private final NotificationUseCase notificationUseCase;

    @Operation(summary = "Get user notifications", description = "Retrieves a paginated list of recent notifications for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT")
    })
    @GetMapping
    public ResponseEntity<NotificationPaginatedResponse> getUserNotifications(
            @Parameter(hidden = true) @AuthenticationPrincipal UserAuthenticated currentUser,
            @Parameter(description = "Page number (zero-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(notificationUseCase.getRecentNotifications(currentUser.id(), page, size));
    }

    @Operation(summary = "Mark notification as read", description = "Marks a specific notification as read by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notification marked as read successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User attempting to read someone else's notification"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(
            @Parameter(description = "UUID of the notification", example = "123e4567-e89b-12d3-a456-426614174000") @PathVariable UUID notificationId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserAuthenticated currentUser
    ) {
        notificationUseCase.markAsRead(notificationId, currentUser.id());
        return ResponseEntity.noContent().build();
    }
}