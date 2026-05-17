package com.example.microchatnotificationservice.application.usecases;

import com.example.microchatnotificationservice.application.exceptions.ForbiddenActionException;
import com.example.microchatnotificationservice.application.gateways.MessageBrokerGateway;
import com.example.microchatnotificationservice.application.gateways.NotificationGateway;
import com.example.microchatnotificationservice.controller.dto.response.NotificationPaginatedResponse;
import com.example.microchatnotificationservice.controller.dto.response.NotificationResponse;
import com.example.microchatnotificationservice.domain.Notification;
import com.example.microchatnotificationservice.infrastructure.persistence.mapper.NotificationMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationUseCase {

    private final MessageBrokerGateway messageBrokerGateway;
    private final NotificationGateway notificationGateway;
    private final NotificationMapper notificationMapper;

    @Transactional
    @CacheEvict(value = "notifications", key = "#notification.receiverId")
    public void saveNotification(Notification notification) {
        notification.setRead(false);

        var savedNotification = notificationGateway.saveNotification(notification);

        var notificationResponse = notificationMapper.domainToResponse(savedNotification);

        sendToBroker(notificationResponse);
    }

    @Cacheable(value = "notifications", key = "#userId")
    public NotificationPaginatedResponse getRecentNotifications(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Notification> notificationPage = notificationGateway.getRecentNotifications(userId, pageable);
        
        List<NotificationResponse> responses = notificationPage.getContent()
                .stream()
                .map(notificationMapper::domainToResponse)
                .toList();

        System.out.println(responses);

        return NotificationPaginatedResponse.builder()
                .content(responses)
                .currentPage(notificationPage.getNumber())
                .totalPages(notificationPage.getTotalPages())
                .totalElements(notificationPage.getTotalElements())
                .build();
    }

    @Transactional
    @CacheEvict(value = "notifications", key = "#userId")
    public void markAsRead(UUID notificationId, Long userId) {
        Notification notification = notificationGateway.getNotificationById(notificationId);

        throwIfUserCannotMarkAsRead(notification.getReceiverId(), userId);
        notification.setRead(true);

        notificationGateway.saveNotification(notification);
    }

    private void sendToBroker(NotificationResponse response) {
        System.out.println("Im here!!" + response);
        messageBrokerGateway.convertAndSend(
                "amq.topic",
                "notification." + response.receiverId(),
                response
                );
    }

    private void throwIfUserCannotMarkAsRead(Long receiverId, Long userId) {
        if (!receiverId.equals(userId)) {
            throw new ForbiddenActionException("You can't read this notification");
        }
    }
}
