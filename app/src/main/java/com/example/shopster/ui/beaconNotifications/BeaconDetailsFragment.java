package com.example.shopster.ui.beaconNotifications;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shopster.R;
import com.example.shopster.data.util.DataUtil;
import com.example.shopster.model.Product;
import com.example.shopster.model.adapter.HomeAdapter;
import com.example.shopster.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BeaconDetailsFragment extends Fragment {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference productsRef = database.getReference("products");

    List<Product> products = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beacon_details, container, false);


    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.beaconDetails);

        HomeAdapter homeAdapter = new HomeAdapter(new ArrayList<>());

        homeAdapter.setItemClickListener(new HomeAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("productId", products.get(position).getId());
                bundle.putInt("pos", position);
                Toast.makeText(view.getContext(), "You clicked " + position, Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(BeaconDetailsFragment.this)
                        .navigate(R.id.action_beaconDetailsFragment_to_productDetailsFragment, bundle);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setAdapter(homeAdapter);

        List<String> productsList = List.of(getArguments().getStringArray("products"));


        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Product product = item.getValue(Product.class);
                    product.setId(item.getKey());
                    if(!productsList.contains(product.getId()))
                        continue;
                    DataUtil.productList.add(product);
                    products.add(product);
                }

                homeAdapter.updateData(products);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }
}