package com.thp.shopster.ui.notifications;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    }
}