package com.example.microchatnotificationservice.infrastructure.gateways;

import com.example.microchatnotificationservice.application.exceptions.NotificationNotFoundException;
import com.example.microchatnotificationservice.application.gateways.NotificationGateway;
import com.example.microchatnotificationservice.domain.Notification;
import com.example.microchatnotificationservice.infrastructure.persistence.NotificationRepository;
import com.example.microchatnotificationservice.infrastructure.persistence.entities.NotificationEntity;
import com.example.microchatnotificationservice.infrastructure.persistence.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryGateway implements NotificationGateway {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification saveNotification(Notification notification) {
        var notificationEntity = notificationMapper.domainToEntity(notification);

        var savedNotification = notificationRepository.save(notificationEntity);

        return notificationMapper.entityToDomain(savedNotification);
    }

    @Override
    public Page<Notification> getRecentNotifications(Long userId, Pageable pageable) {
        Page<NotificationEntity> notificationEntities = notificationRepository.findByReceiverId(userId, pageable);

        System.out.println(notificationEntities);
        return notificationEntities.map(notificationMapper::entityToDomain);
    }

    @Override
    public Notification getNotificationById(UUID notificationId) {
        NotificationEntity notificationEntity = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));

        return notificationMapper.entityToDomain(notificationEntity);
    }
}
