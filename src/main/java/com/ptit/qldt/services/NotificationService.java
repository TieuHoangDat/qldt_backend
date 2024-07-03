package com.ptit.qldt.services;

import com.ptit.qldt.dtos.NotificationDto;
import com.ptit.qldt.models.Notification;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> findAllNotification();


    Notification save(Notification notification);

    void deleteNotificationById(int notificationId);

    NotificationDto findById(int notificationId);

    void updateNotification(NotificationDto notificationDto);
}
