package com.example.microchatnotificationservice.application.gateways;

import com.example.microchatnotificationservice.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationGateway {
    Notification saveNotification(Notification notification);

    Page<Notification> getRecentNotifications(Long userId, Pageable pageable);

    Notification getNotificationById(UUID notificationId);
}
