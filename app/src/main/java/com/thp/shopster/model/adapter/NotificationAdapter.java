package com.thp.shopster.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thp.shopster.R;
import com.thp.shopster.model.Notification;
import com.thp.shopster.model.Product;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    private NotificationAdapter.NotificationClickListener notificationClickListener;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, null, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.bindData(this.notifications.get(position));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void updateData(List<Notification> notifications) {
        this.notifications = notifications;
        this.notifyDataSetChanged();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView fromNotification, dateTimeNotification;
        private ImageView imgNotification;
        private View view;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            fromNotification = (TextView) itemView.findViewById(R.id.txtFrom);
            dateTimeNotification = (TextView) itemView.findViewById(R.id.txtNotificationDateTime);
            imgNotification = (ImageView) itemView.findViewById(R.id.imgNotification);
            view = itemView;
        }

        public void bindData(final Notification notification) {
            fromNotification.setText(notification.getNotificationId() + notification.getMessage());
            imgNotification.setImageResource(R.drawable.g305);
            dateTimeNotification.setText(notification.getDateTime().toString());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notificationClickListener.onItemClicked(getAdapterPosition());
                }
            });

            imgNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), "you clicked on image", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public interface NotificationClickListener{
        void onItemClicked(int position);
    }

    public void setItemClickListener(NotificationAdapter.NotificationClickListener notificationClickListener) {
        this.notificationClickListener = notificationClickListener;
    }
}
