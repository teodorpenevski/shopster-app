package com.example.shopster.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountStore {

    private String id;
    private String name;

    private HashMap<String, Boolean> products;

    public DiscountStore() {
        products = new HashMap<>();
    }

    public DiscountStore(String id, String name, String description, HashMap<String, Boolean> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String [] getProducts() {
        List<String> strings = new ArrayList<>(products.keySet());
        String [] productsIds = new String[products.size()];
        for(int i=0; i<strings.size(); i++){
            productsIds[i] = strings.get(i);
        }
        return productsIds;
    }

}
