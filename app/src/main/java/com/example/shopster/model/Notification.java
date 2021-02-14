package com.example.shopster.model;

import java.time.ZonedDateTime;

public class Notification {

    private String notificationId;
    private String senderId;
    private String message;
    private ZonedDateTime dateTime;

    public Notification(String notificationId, String senderId, String message, ZonedDateTime dateTime) {
        this.notificationId = notificationId;
        this.senderId = senderId;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Notification() {
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
