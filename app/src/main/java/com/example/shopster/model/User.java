package com.example.shopster.model;

public class User {

    String name;

    String username;

    String cart;

    public User() {
    }

    public User(String name, String username, String cart) {
        this.name = name;
        this.username = username;
        this.cart = cart;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getCart() {
        return cart;
    }
}
