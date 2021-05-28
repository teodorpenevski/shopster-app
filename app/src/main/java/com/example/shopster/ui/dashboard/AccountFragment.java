package com.example.shopster.ui.dashboard;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopster.LoginActivity;
import com.example.shopster.MainActivity;
import com.example.shopster.MainActivity2;
import com.example.shopster.R;
import com.example.shopster.ui.home.HomeFragment;
import com.example.shopster.ui.notifications.NotificationsFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AccountFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    private ProductDetailsViewModel productDetailsViewModel;
    private View view;

    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            view.findViewById(R.id.btnLogin).setVisibility(View.GONE);
        }
        else{
            view.findViewById(R.id.btnLogin).setVisibility(View.VISIBLE);

        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
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
        view = root;
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnWishList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AccountFragment.this)
                        .navigate(R.id.action_navigation_dashboard_to_wishListFragment);
            }
        });

        view.findViewById(R.id.beaconDisc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AccountFragment.this)
                        .navigate(R.id.action_navigation_dashboard_to_beaconNotificationsFragment);
            }
        });

        view.findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser() != null){
                    Toast.makeText(getContext(), "Already logged in!", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                Toast.makeText(getContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
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
//

//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
//        stackBuilder.addNextIntentWithParentStack(intent);

//        Intent mainIntent = new Intent(getContext(), MainActivity.class);

//        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
//
//        Notification notification = new NotificationCompat.Builder(getContext(), "id1")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("New Notification")
//                .setContentText("Hello. I am notification. Click me!")
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setContentIntent(pendingIntent)
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