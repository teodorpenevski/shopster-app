package com.example.shopster.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopster.R;
import com.example.shopster.model.DiscountStore;
import com.example.shopster.model.Product;
import com.example.shopster.model.Store;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BeaconAdapter extends RecyclerView.Adapter<BeaconAdapter.BeaconViewHolder> {

    private List<DiscountStore> stores;
    private BeaconAdapter.ItemClickListener itemClickListener;

    public BeaconAdapter(List<DiscountStore> stores) {
        this.stores = stores;
    }

    @NonNull
    @Override
    public BeaconAdapter.BeaconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null, false);
        return new BeaconAdapter.BeaconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeaconAdapter.BeaconViewHolder holder, int position) {
        holder.bindData(this.stores.get(position));
    }

    public void updateData(List<DiscountStore> stores) {
        this.stores = stores;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.stores.size();
    }

    class BeaconViewHolder extends RecyclerView.ViewHolder {

        private TextView nameProduct, priceProduct;
        private ImageView imageProduct;
        private View view;

        public BeaconViewHolder(@NonNull View itemView) {
            super(itemView);

            nameProduct = (TextView) itemView.findViewById(R.id.productName);
            imageProduct = (ImageView) itemView.findViewById(R.id.productImg);
            priceProduct = (TextView) itemView.findViewById(R.id.productPrice);
            view = itemView;
        }

        public void bindData(final DiscountStore store) {
            nameProduct.setText(store.getName());
            priceProduct.setText("Discounts up tp 70%");
            Picasso.get()
                    .load("file:///android_asset/img/iphone-8plus.png")
                    .fit()
                    .centerCrop()
                    .into(imageProduct);

            itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClicked(getAdapterPosition());
                }
            });

        }
    }

    public interface ItemClickListener {
        void onItemClicked(int position);
    }

    public void setItemClickListener(BeaconAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}