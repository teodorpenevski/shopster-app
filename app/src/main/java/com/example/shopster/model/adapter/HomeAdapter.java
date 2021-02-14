package com.example.shopster.model.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopster.R;
import com.example.shopster.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<Product> products;
    private ItemClickListener itemClickListener;

    public HomeAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bindData(this.products.get(position));
    }

    public void updateData(List<Product> products) {
        this.products = products;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder{

        private TextView nameProduct, priceProduct;
        private ImageView imageProduct;
        private View view;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            nameProduct = (TextView) itemView.findViewById(R.id.productName);
            imageProduct = (ImageView) itemView.findViewById(R.id.productImg);
            priceProduct = (TextView) itemView.findViewById(R.id.productPrice);
            view = itemView;
        }

        public void bindData(final Product product) {
            nameProduct.setText(product.getName());
            priceProduct.setText(String.valueOf(product.getPrice()) + "$");
            Picasso.get()
                    .load(product.getImageURL().get(0))
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

    public interface ItemClickListener{
        void onItemClicked(int position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
