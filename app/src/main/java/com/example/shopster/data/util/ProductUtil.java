package com.example.shopster.data.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.shopster.model.Product;
import com.example.shopster.model.Review;

import java.time.ZonedDateTime;

public class ProductUtil {
    public static Product product1 = new Product("unique1", "Product 1", "A computer mouse (plural mice, rarely mouses) is a hand-held pointing device that detects two-dimensional motion relative to a surface. This motion is typically translated into the motion of a pointer on a display, which allows a smooth control of the graphical user interface of a computer.", "url" , 24.99, 9.96);
    public static Product product2 = new Product("unique2", "Product 2", "123 321", "url", 25.99, 8.96);
    public static Product product3 = new Product("unique3", "Product 3", "123 321", "url", 44.99, 7.96);
    public static Product product4 = new Product("unique4", "Product 3", "123 321", "url", 124.99, 9.46);

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void initReviews(){
        product1.addReview(new Review("user1", "Mnogu dobra muzika u pm, ama i majcava e p ti m", 10, ZonedDateTime.now()));
        product1.addReview(new Review("user2", "Mnogu dobraaa muzika u pm i majcava e p ti m", 9, ZonedDateTime.now()));
        product1.addReview(new Review("user3", "Mnogu dobraa muzika u pm, ama i majcava e p ti m", 8, ZonedDateTime.now()));


    }
}
