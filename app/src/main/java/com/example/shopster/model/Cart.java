package com.example.shopster.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String cartId;
    List<CartUnit> productsInCart;
    boolean created;



    public Cart() {
        this.productsInCart = new ArrayList<>();
    }

    public void addToCart(String product, int quantity){
        CartUnit unit = new CartUnit(product, quantity);
        this.productsInCart.add(unit);
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public List<CartUnit> getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(List<CartUnit> productsInCart) {
        this.productsInCart = productsInCart;
    }
}