package com.example.shopster.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.shopster.model.enumeration.NotificationType;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Notification {

    private String notificationId;
    private String senderId;
    private String message;
    private ZonedDateTime dateTime;
    private String notificationType;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification(String senderId, String message, long dateTime, String notificationType) {
        this.message = message;
        this.dateTime = ZonedDateTime.now();
        this.notificationType = notificationType;
    }

    public Notification() {
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public String getNotificationId() {
        return notificationId;
    }


    public String getMessage() {
        return message;
    }
}
