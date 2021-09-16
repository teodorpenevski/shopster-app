package com.example.shopster.model;

import java.time.ZonedDateTime;

public class Review {
    private String userId;
    private String comment;
    private int rating;
    private ZonedDateTime dateTime;

    public Review(String userId, String comment, int rating, ZonedDateTime dateTime) {
        this.userId = userId;
        this.comment = comment;
        this.rating = rating;
        this.dateTime = dateTime;
    }

    public Review() {
    }

    public String getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
