package com.example.shopster.ui.details;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.shopster.R;
import com.example.shopster.data.FakeApi;
import com.example.shopster.data.util.DataUtil;
import com.example.shopster.model.HeightWrapViewPager;
import com.example.shopster.model.Product;
import com.example.shopster.model.Review;
import com.example.shopster.model.adapter.ReviewAdapter;
import com.example.shopster.model.adapter.ViewPagerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class ProductDetailsFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference productsRef = database.getReference("products");
    DatabaseReference reviewsRef = database.getReference("productsReviews");
//
//    DatabaseReference current = new Da();

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
        HeightWrapViewPager viewPager;


        tvProductName = view.findViewById(R.id.txtProductName);
        tvPrice = view.findViewById(R.id.txtPrice);
        tvDescription = view.findViewById(R.id.txtDescription);
        viewPager = view.findViewById(R.id.view_pager);

        int position = getArguments().getInt("pos");
        Product current = DataUtil.productList.get(position);

        tvProductName.setText(current.getName());
        tvPrice.setText(String.valueOf(current.getPrice()));
        tvDescription.setText(current.getDescription());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.getContext());
        viewPagerAdapter.setImageURIs(current.getImageURL());
        viewPager.setAdapter(viewPagerAdapter);


        RecyclerView reviewRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_review);

        ReviewAdapter reviewAdapter = new ReviewAdapter(new ArrayList<>());
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewRecyclerView.setAdapter(reviewAdapter);
//
//        reviewsRef.child(current.getId()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                snapshot.getChildren().forEach(x -> x.getValue(String.class));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

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