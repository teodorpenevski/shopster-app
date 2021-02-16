package com.example.shopster.model;

public class ProductReview {

    String review;

    String user;

    public ProductReview(String review, String user) {
        this.review = review;
        this.user = user;
    }

    public ProductReview() {
    }

    public String getReview() {
        return review;
    }

    public String getUser() {
        return user;
    }
}
