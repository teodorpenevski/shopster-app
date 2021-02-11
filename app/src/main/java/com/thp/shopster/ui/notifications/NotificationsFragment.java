package com.thp.shopster.ui.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thp.shopster.MainActivity;
import com.thp.shopster.MainActivity2;
import com.thp.shopster.R;
import com.thp.shopster.model.Notification;
import com.thp.shopster.model.Product;
import com.thp.shopster.model.adapter.HomeAdapter;
import com.thp.shopster.model.adapter.NotificationAdapter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public static int NUM = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notificationRecyclerView = (RecyclerView) view.findViewById(R.id.notification_recycler);

        NotificationAdapter notificationAdapter = new NotificationAdapter(new ArrayList<>());
        notificationAdapter.setItemClickListener(new NotificationAdapter.NotificationClickListener() {
            @Override
            public void onItemClicked(int position) {
                Toast.makeText(getContext(), "The notification #" + position + " will open soon!", Toast.LENGTH_SHORT).show();
            }
        });

        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        notificationRecyclerView.setAdapter(notificationAdapter);

        List<Notification> list = new ArrayList<>();

        list.add(new Notification("id1", "us1", "you have new notification!", ZonedDateTime.now()));
        list.add(new Notification("id2", "us1", "you have new 2 notification!", ZonedDateTime.now()));
        list.add(new Notification("id3", "us1", "you have new 3 notification!", ZonedDateTime.now()));

        notificationAdapter.updateData(list);


        NotificationManager notificationManager = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "name";
//            String description = "desc";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("id1", name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//
//
//        }

        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getContext(), MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        stackBuilder.addNextIntentWithParentStack(intent);

        Intent mainIntent = new Intent(getContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivities(getContext(), 0, new Intent[]{mainIntent, intent}, PendingIntent.FLAG_ONE_SHOT);

        android.app.Notification notification = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_home_black_24dp)
                .setContentTitle("Notification 2")
                .setContentText("Hello. I am notification 2. Click me 2!")
                .setChannelId("id1")
                .setContentIntent(pendingIntent)
                .build();

        // Cancel the notification after its selected
        notification.flags |= android.app.Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(NotificationsFragment.NUM++, notification);
    }
}