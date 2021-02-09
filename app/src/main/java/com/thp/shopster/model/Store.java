package com.thp.shopster.model;

import java.util.List;

public class Store {

    private String id;
    private String name;
    private String description;

    private List<Product> products;

    public Store(String id, String name, String description, List<Product> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }
}