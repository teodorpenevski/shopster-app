package com.example.shopster.ui.beaconNotifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.shopster.model.DiscountStore;
import com.example.shopster.model.Product;
import com.example.shopster.model.Store;
import com.example.shopster.model.adapter.BeaconAdapter;
import com.example.shopster.model.adapter.HomeAdapter;
import com.example.shopster.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BeaconNotificationsFragment extends Fragment {


    FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopster-eshop-default-rtdb.firebaseio.com/");
    DatabaseReference storesRef = database.getReference("discounts");

    List<DiscountStore> stores = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beacon_notifications, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.beaconRecycler);

        BeaconAdapter beaconAdapter = new BeaconAdapter(new ArrayList<>());

        beaconAdapter.setItemClickListener(new BeaconAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("storeId", stores.get(position).getId());
                bundle.putInt("pos", position);
                bundle.putStringArray("products", stores.get(position).getProducts());
                Toast.makeText(view.getContext(), "You clicked " + position, Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(BeaconNotificationsFragment.this)
                        .navigate(R.id.action_beaconNotificationsFragment_to_beaconDetailsFragment, bundle);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setAdapter(beaconAdapter);



        storesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stores = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    DiscountStore store = item.getValue(DiscountStore.class);
                    store.setId(item.getKey());
//                    DataUtil.productList.add(product);
                    stores.add(store);
                }

                beaconAdapter.updateData(stores);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }
}