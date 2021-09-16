package com.example.shopster.ui.details;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopster.R;
import com.example.shopster.data.FakeApi;
import com.example.shopster.model.Product;
import com.example.shopster.model.Review;
import com.example.shopster.model.adapter.ReviewAdapter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class ProductDetailsFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvProductName, tvPrice, tvDescription;
        ImageView imgProduct;


        tvProductName = view.findViewById(R.id.txtProductName);
        tvPrice = view.findViewById(R.id.txtPrice);
        tvDescription = view.findViewById(R.id.txtDescription);
        imgProduct = view.findViewById(R.id.imgProduct);

        int position = getArguments().getInt("ProductId");
        Product current = FakeApi.getInstance().getProducts().get(position);

        tvProductName.setText(current.getName());
        tvPrice.setText(String.valueOf(current.getPrice()));
        tvDescription.setText(current.getDescription());
        imgProduct.setImageResource(R.drawable.g305);


        RecyclerView reviewRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_review);

        ReviewAdapter reviewAdapter = new ReviewAdapter(new ArrayList<>());
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewRecyclerView.setAdapter(reviewAdapter);

        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("id1", "very good", 5, ZonedDateTime.now()));
        reviews.add(new Review("id2", "not so bad", 3, ZonedDateTime.now()));
        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));

        reviewAdapter.updateData(reviews);

    }
}