package com.example.shopster.ui.details;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
import com.example.shopster.model.ProductReview;
import com.example.shopster.model.Review;
import com.example.shopster.model.User;
import com.example.shopster.model.adapter.ReviewAdapter;
import com.example.shopster.model.adapter.ViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        String pos = getArguments().getString("productId");
        DatabaseReference productsRef = database.getReference("products/" + pos);

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Product current = (Product) snapshot.getValue(Product.class);
                tvProductName.setText(current.getName());
                tvPrice.setText(String.valueOf(current.getPrice()));
                tvDescription.setText(current.getDescription());
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
                viewPagerAdapter.setImageURIs(current.getImageURL());
                viewPager.setAdapter(viewPagerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error", error.getMessage());
            }
        });

        DatabaseReference reviewsRef = database.getReference("productsReviews");


        RecyclerView reviewRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_review);

        ReviewAdapter reviewAdapter = new ReviewAdapter(new ArrayList<>());
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewRecyclerView.setAdapter(reviewAdapter);

        List<Review> reviews = new ArrayList<>();
        reviewsRef.child(pos).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ProductReview> productReviews = new ArrayList<>();
                snapshot.getChildren().forEach(s->  productReviews.add(s.getValue(ProductReview.class)));

                productReviews.forEach(s-> {
                    DatabaseReference review = database.getReference("review");
//                    Review review1 = new Review();
                    final String[] userId = new String[1];
                    review.child(s.getReview()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final Review review = snapshot.getValue(Review.class);
                            DatabaseReference userRef = database.getReference("user");
                            userRef.child(s.getUser()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);
                                    userId[0] = user.getUsername();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            reviews.add(new Review(userId[0], review.getComment(), review.getRating(), ZonedDateTime.now()));
//                            review.setUserId(userId[0]);
                            reviewAdapter.updateData(reviews);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<Review> func(String reviewId, List<Review> reviews){
        DatabaseReference review = database.getReference("review");
        review.child(reviewId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Review review = snapshot.getValue(Review.class);
//                reviews.add(new Review(review.getComment(), review.getRating(), "datetime"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return reviews;
    }

//        DatabaseReference reviewReff = database.getReference("review/" + productReview[0].getReview());
//        DatabaseReference userReff = database.getReference("user/" + productReview[0].getUser());



//        reviewReff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                final Review review = snapshot.getValue(Review.class);
//                reviews.add(new Review(review.getComment(), review.getRating(), review.getDateTime()));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

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

//        List<Review> reviews = new ArrayList<>();
//        reviews.add(new Review("id1", "very good", 5, ZonedDateTime.now()));
//        reviews.add(new Review("id2", "not so bad", 3, ZonedDateTime.now()));
//        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
//        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
//        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
//        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
//        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
//        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
//        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
//        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
//        reviews.add(new Review("id3", "very good", 4, ZonedDateTime.now()));
//        final ProductReview[] productReview = new ProductReview[1];

}