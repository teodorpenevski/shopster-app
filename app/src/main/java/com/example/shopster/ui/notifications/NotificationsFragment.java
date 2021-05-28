package com.example.shopster.ui.notifications;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopster.MainActivity;
import com.example.shopster.MainActivity2;
import com.example.shopster.R;
import com.example.shopster.model.Notification;
import com.example.shopster.model.adapter.NotificationAdapter;
import com.example.shopster.ui.wishlist.WishListFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userNotifications = database.getReference("userNotifications");
    DatabaseReference notification = database.getReference("notification");
    private FirebaseAuth mAuth;

    List<String> notificationIds = new ArrayList<>();
    List<Notification> notifications = new ArrayList<>();

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
            public void onItemClicked(String type) {
                if(type.equals("STORES")){
                    Toast.makeText(getContext(), "Going to stores with discount!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Bundle bundle = new Bundle();
                    bundle.putString("productId", type);
                    Toast.makeText(view.getContext(), "You clicked on product " + type, Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(NotificationsFragment.this)
                            .navigate(R.id.action_navigation_notifications_to_productDetailsFragment, bundle);
                }

            }
        });

        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        notificationRecyclerView.setAdapter(notificationAdapter);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();


        List<Notification> list = new ArrayList<>();

        if(user != null){
            userNotifications.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        notifications = new ArrayList<>();
                        for (DataSnapshot s : task.getResult().getChildren()){
                            notification.child(s.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Notification notification = snapshot.getValue(Notification.class);
                                    notification.setNotificationId(snapshot.getKey());
                                    notifications.add(notification);
                                    notification.setDateTime(ZonedDateTime.now());
                                    notificationAdapter.updateData(notifications);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            });

        }



//        list.add(new Notification("you have new notification!", ZonedDateTime.now()));
//        list.add(new Notification( "you have new 2 notification!", ZonedDateTime.now()));
//        list.add(new Notification("id3", "us1", "you have new 3 notification!", ZonedDateTime.now()));

        notificationAdapter.updateData(list);


//
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        stackBuilder.addNextIntentWithParentStack(intent);

        Intent mainIntent = new Intent(getContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        android.app.Notification notification = new NotificationCompat.Builder(getContext(), "id1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New Notification 2")
                .setContentText("Hello. I am notification 2. Click me 2!")
                .setDefaults(android.app.Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
//                .setChannelId("id1")
//                .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
                .setPriority(android.app.Notification.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_CALL)
                .build();

        // Cancel the notification after its selected
        notification.flags |= android.app.Notification.FLAG_AUTO_CANCEL;

//        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(getContext(), "id1")
//                .setSmallIcon(R.drawable.ic_home_black_24dp)
//                .setContentTitle("My notification")
//                .setContentText("Hello World!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);




// notificationId is a unique int for each notification that you must define
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NotificationsFragment.NUM++, notification);
    }
}