package com.example.shopster.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopster.R;
import com.example.shopster.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    private NotificationClickListener notificationClickListener;

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
        private TextView fromNotification, dateTimeNotification, message;
        private ImageView imgNotification;
        private View view;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            fromNotification = (TextView) itemView.findViewById(R.id.txtFrom);
            message = (TextView) itemView.findViewById(R.id.txtMessaage);
            dateTimeNotification = (TextView) itemView.findViewById(R.id.txtNotificationDateTime);
            imgNotification = (ImageView) itemView.findViewById(R.id.imgNotification);
            view = itemView;
        }

        public void bindData(final Notification notification) {
            fromNotification.setText("Notification from " + notification.getSenderId());
            message.setText(notification.getMessage());
            imgNotification.setImageResource(R.drawable.g305);
            dateTimeNotification.setText(notification.getDateTime().toString());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notificationClickListener.onItemClicked(notification.getNotificationType());
                }
            });

            imgNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Going to stores with discount!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public interface NotificationClickListener{
        void onItemClicked(String position);
    }

    public void setItemClickListener(NotificationClickListener notificationClickListener) {
        this.notificationClickListener = notificationClickListener;
    }
}
