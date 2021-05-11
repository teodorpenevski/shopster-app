package com.example.shopster.ui.details;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.shopster.LoginActivity;
import com.example.shopster.R;
import com.example.shopster.data.FakeApi;
import com.example.shopster.data.util.DataUtil;
import com.example.shopster.model.CartUnit;
import com.example.shopster.model.HeightWrapViewPager;
import com.example.shopster.model.Product;
import com.example.shopster.model.ProductReview;
import com.example.shopster.model.Review;
import com.example.shopster.model.User;
import com.example.shopster.model.adapter.ReviewAdapter;
import com.example.shopster.model.adapter.ViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDetailsFragment extends Fragment {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cartsRef = database.getReference("cart");
    List<String> users = new ArrayList<>();


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();


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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ProductReview> productReviews = new ArrayList<>();
                snapshot.getChildren().forEach(s -> productReviews.add(s.getValue(ProductReview.class)));

                productReviews.forEach(s -> {
                    DatabaseReference review = database.getReference("review");
//                    Review review1 = new Review();
                    final String[] userId = new String[1];
                    review.child(s.getReview()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final Review review = snapshot.getValue(Review.class);
                            DatabaseReference userRef = database.getReference("user");
                            userRef.child(s.getUser()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(task.isSuccessful()){
                                        userId[0] = task.getResult().getValue(User.class).getUsername();
                                        reviews.add(new Review(userId[0], review.getComment(), review.getRating(), ZonedDateTime.now()));
//                            review.setUserId(userId[0]);
                                        reviewAdapter.updateData(reviews);
                                    }
                                    else {
                                        Toast.makeText(getContext(), "dasc", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

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

        DatabaseReference productsUser = database.getReference("productUsers/" + pos);
        DatabaseReference userProducts = database.getReference("userProducts");
        ImageView favorite = (ImageView) view.findViewById(R.id.imageView);

        if(user != null){
            checkIfAlreadyHas(pos, productsUser, favorite, user);
        }


        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null) {
                    changeColor(productsUser, userProducts, pos, favorite, user);
                    Toast.makeText(getContext(), "You added this product to your favorite list!", Toast.LENGTH_SHORT).show();
//                favorite.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
                else{
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
//                    Rect displayRectangle = new Rect();
//                    Window window = getActivity().getWindow();
//                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
//                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
//                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_login, viewGroup, false);
//                    dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
//                    dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
//                    builder.setView(dialogView);
//                    final AlertDialog alertDialog = builder.create();
////                    Button buttonOk=dialogView.findViewById(R.id.buttonOk);
////                    buttonOk.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            alertDialog.dismiss();
////                        }
////                    });
//                    alertDialog.show();
                }
            }
        });

        LinearLayout buttonBar = getActivity().findViewById(R.id.button_bar_buy_add);

        Button addToCart = buttonBar.findViewById(R.id.btnAdd);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = database.getReference("user/");
                String username = mAuth.getCurrentUser().getEmail();
                Query query = usersRef.orderByChild("username").equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot u : snapshot.getChildren()){
                            User user = u.getValue(User.class);
                            String cartId = user.getCart();
                            List<CartUnit> products = new ArrayList();
                            DatabaseReference cartRef = database.getReference("cart/" + cartId);
                            cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.hasChild("products")){
                                        products.add(new CartUnit(pos, 1));
                                        cartsRef.child(cartId).child("products").setValue(products);
                                    }
                                    else{
                                        boolean updated = false;
                                        for(DataSnapshot unit : snapshot.child("products").getChildren()){
                                            if(unit.child("product").getValue().equals(pos)){
                                                products
                                                        .add(new CartUnit(unit.child("product").getValue().toString(), Math.toIntExact((Long) unit.child("quantity").getValue() + 1)));
                                                updated = true;
                                            }
                                            else{
                                                products.add(new CartUnit(unit.child("product").getValue().toString(), Math.toIntExact((Long) unit.child("quantity").getValue())));
                                            }
                                        }
                                        if(!updated){
                                            products.add(new CartUnit(pos, 1));
                                        }
                                        cartsRef.child(cartId).child("products").setValue(products);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            }

                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void changeColor(DatabaseReference productsUser, DatabaseReference userProducts, String pos, ImageView favorite, FirebaseUser user) {
        productsUser.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                users = new ArrayList<>();
                for (DataSnapshot item : task.getResult().getChildren()) {
                    users.add(item.getKey());
                }
                if(users.contains(user.getUid())){
                    productsUser.child(user.getUid()).removeValue();
                    userProducts.child(user.getUid()).child(pos).removeValue();
                    favorite.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                }
                else {
                    productsUser.child(user.getUid()).setValue(true);
                    userProducts.child(user.getUid()).child(pos).setValue(true);
                }
            }

        });

    }

    private void checkIfAlreadyHas(String productId, DatabaseReference productsUser, ImageView favorite, FirebaseUser user) {
        boolean returnValue = false;
        productsUser.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    favorite.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private List<Review> func(String reviewId, List<Review> reviews) {
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
}