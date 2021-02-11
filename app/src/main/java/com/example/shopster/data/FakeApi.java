package com.example.shopster.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.shopster.data.util.ProductUtil;
import com.example.shopster.model.Product;

import java.util.ArrayList;
import java.util.List;

public class FakeApi {

    private  static FakeApi instance;

    private List<Product> products = new ArrayList<Product>();

    private FakeApi(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static FakeApi getInstance(){
        if(instance == null){
            instance = new FakeApi();
            ProductUtil.initReviews();
            instance.products.add(ProductUtil.product1);
            instance.products.add(ProductUtil.product2);
            instance.products.add(ProductUtil.product3);
            instance.products.add(ProductUtil.product4);
        }

        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addNewMovie(Product product) {
        this.products.add(product);
    }

    public void deleteMovie(Product product){
        this.products.remove(product);
    }
}
