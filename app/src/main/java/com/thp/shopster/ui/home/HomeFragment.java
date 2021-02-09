package com.thp.shopster.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thp.shopster.R;
import com.thp.shopster.model.Product;
import com.thp.shopster.model.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler);

        HomeAdapter homeAdapter = new HomeAdapter(new ArrayList<>());

        homeAdapter.setItemClickListener(new HomeAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("ProductId", position);
                Toast.makeText(view.getContext(), "You clicked " + position, Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_navigation_home_to_productDetailsFragment, bundle);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setAdapter(homeAdapter);

        List<Product> list = new ArrayList<>();

        list.add(new Product("unique1", "Product 1", "123 321", "url", 24.99, 9.96));
        list.add(new Product("unique1", "Product 2", "123 321", "url", 24.99, 9.96));
        list.add(new Product("unique1", "Product 3", "123 321", "url", 24.99, 9.96));
        list.add(new Product("unique1", "Product 3", "123 321", "url", 24.99, 9.96));

        homeAdapter.updateData(list);

    }

}