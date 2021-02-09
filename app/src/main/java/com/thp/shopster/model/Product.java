package com.thp.shopster.model;


import com.thp.shopster.model.enumeration.Category;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String id;
    private String name;
    private String description;
    private String imageURL;
    private double price;
    private double rating;

    private Category category;
    private Store store;
    private List<Review> reviewList;

    public Product() {
        this.reviewList = new ArrayList<>();
    }

    public Product(String id, String name, String description, String imageURL, double price, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
        this.rating = rating;
        this.reviewList = new ArrayList<>();
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public void addReview(Review review){
        reviewList.add(review);
    }
}