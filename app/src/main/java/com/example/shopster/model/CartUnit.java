package com.example.shopster.model;


public class CartUnit {
    private String productId;
    private int quantity;

    public CartUnit(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProduct() {
        return productId;
    }

    public void setProduct(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
