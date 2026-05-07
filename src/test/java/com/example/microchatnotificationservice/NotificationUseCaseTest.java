package com.example.microchatnotificationservice;

import com.example.microchatnotificationservice.application.exceptions.ForbiddenActionException;
import com.example.microchatnotificationservice.application.gateways.MessageBrokerGateway;
import com.example.microchatnotificationservice.application.gateways.NotificationGateway;
import com.example.microchatnotificationservice.application.usecases.NotificationUseCase;
import com.example.microchatnotificationservice.controller.dto.response.NotificationPaginatedResponse;
import com.example.microchatnotificationservice.controller.dto.response.NotificationResponse;
import com.example.microchatnotificationservice.domain.Notification;
import com.example.microchatnotificationservice.infrastructure.persistence.mapper.NotificationMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationUseCaseTest {

    @InjectMocks
    private NotificationUseCase notificationUseCase;

    @Mock
    private MessageBrokerGateway messageBrokerGateway;

    @Mock
    private NotificationGateway notificationGateway;

    @Mock
    private NotificationMapper notificationMapper;

    @Test
    @DisplayName("Should successfully save a notification and send it to the broker")
    void shouldSaveNotificationSuccessfully() {
        Notification notification = new Notification();
        notification.setReceiverId(1L);

        Notification savedNotification = new Notification();
        savedNotification.setReceiverId(1L);
        savedNotification.setRead(false);

        NotificationResponse mockResponse = new NotificationResponse(
                UUID.randomUUID(),
                2L,
                1L,
                "NEW_MESSAGE",
                "Hello bro!",
                LocalDateTime.now()
        );

        when(notificationGateway.saveNotification(any(Notification.class))).thenReturn(savedNotification);
        when(notificationMapper.domainToResponse(savedNotification)).thenReturn(mockResponse);

        notificationUseCase.saveNotification(notification);

        assertFalse(notification.getRead());
        verify(notificationGateway, times(1)).saveNotification(notification);
        verify(messageBrokerGateway, times(1)).convertAndSend(
                eq("amq.topic"),
                eq("notification.1"),
                eq(mockResponse)
        );
    }

    @Test
    @DisplayName("Should return a paginated list of notifications")
    void shouldGetRecentNotifications() {
        Long userId = 1L;
        int page = 0;
        int size = 10;

        Notification notification = new Notification();
        Page<Notification> notificationPage = new PageImpl<>(List.of(notification), PageRequest.of(page, size), 1);

        NotificationResponse mockResponse = new NotificationResponse(
                UUID.randomUUID(),
                2L,
                userId,
                "NEW_MESSAGE",
                "Hello bro!",
                LocalDateTime.now()
        );

        when(notificationGateway.getRecentNotifications(eq(userId), any(PageRequest.class))).thenReturn(notificationPage);
        when(notificationMapper.domainToResponse(any(Notification.class))).thenReturn(mockResponse);

        NotificationPaginatedResponse result = notificationUseCase.getRecentNotifications(userId, page, size);

        assertNotNull(result);
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());
        assertEquals(0, result.currentPage());
        assertEquals(1, result.content().size());

        assertEquals(mockResponse, result.content().get(0));

        verify(notificationGateway, times(1)).getRecentNotifications(eq(userId), any(PageRequest.class));
    }

    @Test
    @DisplayName("Should successfully mark a notification as read if user is the receiver")
    void shouldMarkAsReadSuccessfully() {
        UUID notificationId = UUID.randomUUID();
        Long userId = 1L;

        Notification notification = new Notification();
        notification.setReceiverId(userId);
        notification.setRead(false);

        when(notificationGateway.getNotificationById(notificationId)).thenReturn(notification);

        notificationUseCase.markAsRead(notificationId, userId);

        assertTrue(notification.getRead());
        verify(notificationGateway, times(1)).saveNotification(notification);
    }

    @Test
    @DisplayName("Should throw UnauthorizedActionException when marking someone else's notification as read")
    void shouldThrowExceptionWhenUserCannotMarkAsRead() {
        UUID notificationId = UUID.randomUUID();
        Long hackerUserId = 99L;
        Long actualReceiverId = 1L;

        Notification notification = new Notification();
        notification.setReceiverId(actualReceiverId);

        when(notificationGateway.getNotificationById(notificationId)).thenReturn(notification);

        ForbiddenActionException exception = assertThrows(
                ForbiddenActionException.class,
                () -> notificationUseCase.markAsRead(notificationId, hackerUserId)
        );

        assertEquals("You can't read this notification", exception.getMessage());
        verify(notificationGateway, never()).saveNotification(any(Notification.class));
    }
}