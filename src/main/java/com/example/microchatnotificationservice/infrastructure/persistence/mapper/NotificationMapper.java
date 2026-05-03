package com.example.microchatnotificationservice.infrastructure.persistence.mapper;

import com.example.microchatnotificationservice.controller.dto.NotificationEventDto;
import com.example.microchatnotificationservice.controller.dto.response.NotificationResponse;
import com.example.microchatnotificationservice.domain.Notification;
import com.example.microchatnotificationservice.infrastructure.persistence.entities.NotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification eventToDomain(NotificationEventDto dto);

    NotificationResponse domainToResponse(Notification notification);

    NotificationEntity domainToEntity(Notification notification);

    Notification entityToDomain(NotificationEntity entity);
}
