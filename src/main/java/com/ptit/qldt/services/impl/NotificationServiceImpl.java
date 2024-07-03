package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.NotificationDto;
import com.ptit.qldt.models.Notification;
import com.ptit.qldt.repositories.NotificationRepository;
import com.ptit.qldt.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.NotificationMapper.mapToNotification;
import static com.ptit.qldt.mappers.NotificationMapper.mapToNotificationDto;


@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationDto> findAllNotification() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream().map(notification -> mapToNotificationDto(notification)).collect(Collectors.toList());
    }


    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotificationById(int notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public NotificationDto findById(int notificationId) {
        Notification notification = notificationRepository.findById(notificationId).get();
        return mapToNotificationDto(notification);
    }

    @Override
    public void updateNotification(NotificationDto notificationDto) {
        Notification notification = mapToNotification(notificationDto);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }
}
