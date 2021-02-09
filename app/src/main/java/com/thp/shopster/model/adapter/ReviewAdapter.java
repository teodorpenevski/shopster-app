package com.thp.shopster.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thp.shopster.R;
import com.thp.shopster.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review, null, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bindData(this.reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView user, comment, rating, dateTime;
        private ImageView userImg;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            user = (TextView) itemView.findViewById(R.id.txtUser);
            userImg = (ImageView) itemView.findViewById(R.id.imgUser);
            comment = (TextView) itemView.findViewById(R.id.txtComment);
            dateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            rating = (TextView) itemView.findViewById(R.id.txtRating);
        }

        public void bindData(final Review review){
            user.setText(review.getUserId());
            comment.setText(review.getComment());
            dateTime.setText(review.getDateTime().toString());
            rating.setText(review.getRating());
        }
    }
}
