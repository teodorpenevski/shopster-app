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

import com.example.shopster.R;
import com.example.shopster.data.FakeApi;
import com.example.shopster.model.Product;


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

    }
}