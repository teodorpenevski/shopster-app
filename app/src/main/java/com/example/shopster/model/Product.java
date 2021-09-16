package com.example.shopster.model;


import com.example.shopster.model.enumeration.Category;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String id;
    private String name;
    private String description;
    private List<String> imageURL;
    private double price;
    private double rating;

    private Category category;
    private Store store;
    private List<Review> reviewList;

    public Product() {
        this.reviewList = new ArrayList<>();
    }

    public Product(String name, String description, List<String> imageURL, double price, double rating) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
        this.rating = rating;
        this.reviewList = new ArrayList<>();
    }

    public List<String> getImageURL() {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(List<String> imageURL) {
        this.imageURL = imageURL;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}