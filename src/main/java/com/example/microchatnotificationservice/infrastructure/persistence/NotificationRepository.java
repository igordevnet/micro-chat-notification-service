package com.example.microchatnotificationservice.infrastructure.persistence;

import com.example.microchatnotificationservice.infrastructure.persistence.entities.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

    Page<NotificationEntity> findByReceiverId(Long receiverId, Pageable pageable);
}
