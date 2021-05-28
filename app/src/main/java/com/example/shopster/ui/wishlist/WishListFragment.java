package com.example.shopster.ui.wishlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shopster.LoginActivity;
import com.example.shopster.R;
import com.example.shopster.model.Product;
import com.example.shopster.model.User;
import com.example.shopster.model.adapter.HomeAdapter;
import com.example.shopster.model.adapter.WishListAdapter;
import com.example.shopster.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WishListFragment extends Fragment {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference productsRef = database.getReference("products");
    DatabaseReference userProducts = database.getReference("userProducts");

    ValueEventListener vel;
    List<String> productsIds = new ArrayList<>();
    List<Product> products = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.wishlist_recycle);

        WishListAdapter wishListAdapter = new WishListAdapter(new ArrayList<>());

        wishListAdapter.setItemClickListener(new WishListAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(String productId) {
                Bundle bundle = new Bundle();
                bundle.putString("productId", productId);
                Toast.makeText(view.getContext(), "You clicked on product " + productId, Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(WishListFragment.this)
                        .navigate(R.id.action_wishListFragment_to_productDetailsFragment, bundle);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(wishListAdapter);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();


        if(user != null){
            this.vel = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    products = new ArrayList<>();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        productsRef.child(dataSnapshot.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if(task.isSuccessful()) {
                                    Product product = task.getResult().getValue(Product.class);
                                    product.setId(task.getResult().getKey());
                                    products.add(product);
                                    wishListAdapter.updateData(products);
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            userProducts.child(user.getUid()).addValueEventListener(this.vel);
        }
        else {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(this.vel != null)
            userProducts.removeEventListener(this.vel);
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}