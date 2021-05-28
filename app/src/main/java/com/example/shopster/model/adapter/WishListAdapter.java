package com.example.shopster.model.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopster.R;
import com.example.shopster.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListAdapterHolder> {

    private List<Product> products;
    private WishListAdapter.ItemClickListener itemClickListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;


    public WishListAdapter(List<Product> products) {
        this.products = products;
        mAuth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public WishListAdapter.WishListAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist, null, false);
        return new WishListAdapter.WishListAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapterHolder holder, int position) {
        holder.bindData(products.get(position));
    }


    public void updateData(List<Product> products) {
        this.products = products;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    class WishListAdapterHolder extends RecyclerView.ViewHolder{

        private TextView nameProduct, priceProduct;
        private ImageView imageProduct, favorite;
        private View view;

        public WishListAdapterHolder(@NonNull View itemView) {
            super(itemView);

            nameProduct = (TextView) itemView.findViewById(R.id.txtNameW);
            imageProduct = (ImageView) itemView.findViewById(R.id.imgProductW);
            favorite = (ImageView) itemView.findViewById(R.id.favoriteIcon);
            priceProduct = (TextView) itemView.findViewById(R.id.txtPriceW);
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

            favorite.setColorFilter(Color.RED);

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    DatabaseReference productsUser = database.getReference("productUsers/" + product.getId());
                    DatabaseReference userProducts = database.getReference("userProducts");
                    productsUser.child(user.getUid()).removeValue();
                    userProducts.child(user.getUid()).child(product.getId()).removeValue();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClicked(product.getId());
                }
            });

        }
    }

    public interface ItemClickListener{
        void onItemClicked(String productId);
    }

    public void setItemClickListener(WishListAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
