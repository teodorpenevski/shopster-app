package com.thp.shopster.ui.account;

import android.app.Notification;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.thp.shopster.MainActivity;
import com.thp.shopster.MainActivity2;
import com.thp.shopster.R;
import com.thp.shopster.ui.home.HomeFragment;
import com.thp.shopster.ui.notifications.NotificationsFragment;

public class AccountFragment extends Fragment {

    private ProductDetailsViewModel productDetailsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productDetailsViewModel =
                new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        productDetailsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        createNotificationChannel();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = new NotificationChannel("id1", "name", NotificationManager.IMPORTANCE_HIGH);
//            channel.setDescription("desc");
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(channel);
//
//        }
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
////        stackBuilder.addNextIntentWithParentStack(intent);
//
////        Intent mainIntent = new Intent(getContext(), MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
//
//        Notification notification = new NotificationCompat.Builder(getContext(), "id1")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("New Notification")
//                .setContentText("Hello. I am notification. Click me!")
//                .setDefaults(Notification.DEFAULT_ALL)
////                .setChannelId("id1")
////                .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
//                .setPriority(Notification.PRIORITY_HIGH)
////                .setCategory(NotificationCompat.CATEGORY_CALL)
//                .build();
//
//        // Cancel the notification after its selected
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
////        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(getContext(), "id1")
////                .setSmallIcon(R.drawable.ic_home_black_24dp)
////                .setContentTitle("My notification")
////                .setContentText("Hello World!")
////                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
////                // Set the intent that will fire when the user taps the notification
////                .setContentIntent(pendingIntent)
////                .setAutoCancel(true);
//
//
//
//
//// notificationId is a unique int for each notification that you must define
//        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1, notification);
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "desc";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("id1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

        }
    }
}