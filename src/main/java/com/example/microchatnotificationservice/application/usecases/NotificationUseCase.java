package com.example.microchatnotificationservice.application.usecases;

import com.example.microchatnotificationservice.controller.dto.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationUseCase {

    public Notificaton saveNotification(Notification notification) {

    }

    public List<NotificationResponse> getRecentNotifications(Long id) {
    }

    public void markAsRead(String notificationId, Long id) {
    }
}
