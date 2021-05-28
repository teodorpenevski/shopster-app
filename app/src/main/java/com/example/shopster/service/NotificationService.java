package com.example.shopster.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.shopster.MainActivity;
import com.example.shopster.MainActivity2;
import com.example.shopster.R;
import com.example.shopster.model.Notification;
import com.example.shopster.model.Product;
import com.example.shopster.ui.details.ProductDetailsFragment;
import com.example.shopster.ui.notifications.NotificationsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    private Timer timer;
    private Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference notification = database.getReference("notification");
    DatabaseReference products = database.getReference("products");

    private static boolean flag = false;
    List<String> notificationIds;

    private FirebaseAuth mAuth;

    public NotificationService() {
    }



    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Log.d("Log", "Running");
//            startNotify();
        }
    };

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
//            timer = new Timer();
//            timer.schedule(timerTask, 8000, 2*3000);
        }
        @Override
        public void handleMessage(Message msg) {

            mAuth = FirebaseAuth.getInstance();

            FirebaseUser user = mAuth.getCurrentUser();

            if(user != null){
                DatabaseReference userNotifications = database.getReference("userNotifications/" + user.getUid());
                userNotifications.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()) {
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                notificationIds.add(snapshot.getKey());
                            }
                            userNotifications.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    if(previousChildName != null && !previousChildName.equals(notificationIds.get(notificationIds.size() - 1)))
                                        return;
                                    String notificationId = snapshot.getKey();
                                    notification.child(notificationId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if(task.isSuccessful())
                                                startNotify(task.getResult().getValue(Notification.class));
                                        }
                                    });
                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                }

                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                }

                                @Override
                                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });

            }

//            notification.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    startNotify();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
//            try {
//                Thread.sleep(5000);
//
//                startNotify();
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
//
//                android.app.Notification notification = new NotificationCompat.Builder(getContext(), "id1")
//                        .setSmallIcon(R.drawable.ic_launcher_foreground)
//                        .setContentTitle("New Notification 2")
//                        .setContentText("Hello. I am notification 2. Click me 2!")
//                        .setDefaults(android.app.Notification.DEFAULT_ALL)
//                        .setContentIntent(pendingIntent)
////                .setChannelId("id1")
////                .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
//                        .setPriority(android.app.Notification.PRIORITY_HIGH)
////                .setCategory(NotificationCompat.CATEGORY_CALL)
//                        .build();
//
//                // Cancel the notification after its selected
//                notification.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
//                NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(NotificationsFragment.NUM++, notification);
//            } catch (InterruptedException e) {
//                // Restore interrupt status.
//                Thread.currentThread().interrupt();
//            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
//            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        timer = new Timer();
//        timer.schedule(timerTask, 8000, 2*3000);
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        if(!flag)
            flag = true;
        else return START_STICKY;

        notificationIds = new ArrayList<>();

        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        try {
            timer.cancel();
            timerTask.cancel();
        }
        catch (Exception e){
            e.printStackTrace();
        }
//        Intent intent = new Intent("com.example.sample");
//        intent.putExtra("yy", "xx");
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
//        sendBroadcast(intent);
    }

    public void startNotify(Notification notificationArrived){

                String title;
                if(!notificationArrived.getNotificationType().equals("STORES")) {
                    products.child(notificationArrived.getNotificationType()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful()){
                                Product product = task.getResult().getValue(Product.class);
                                product.setId(task.getResult().getKey());
                                Bundle bundle = new Bundle();
                                bundle.putString("productId", product.getId());
                                buildNotification(product.getName(), notificationArrived, bundle, product);
                            }
                        }
                    });
                }

    }

    private void buildNotification(String title, Notification notificationArrived, Bundle bundle, Product product){

        Intent intent = new Intent(getBaseContext(), ProductDetailsFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtras(bundle);

//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
//        stackBuilder.addNextIntentWithParentStack(intent);

//        Intent mainIntent = new Intent(getContext(), MainActivity.class);

        PendingIntent pendingIntent = new NavDeepLinkBuilder(getBaseContext())
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(R.id.productDetailsFragment)
                .setArguments(bundle)
                .createPendingIntent();

        android.app.Notification notification = new NotificationCompat.Builder(getBaseContext(), "id1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title + " is on discount!")
                .setContentText(notificationArrived.getMessage())
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
        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NotificationsFragment.NUM++, notification);
//        Intent intent = new Intent(getBaseContext(), MainActivity2.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtras(bundle);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
//
//        android.app.Notification notification = new NotificationCompat.Builder(getBaseContext(), "id1")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle(title + " is on discount!")
//                .setContentText(notificationArrived.getMessage())
//                .setDefaults(android.app.Notification.DEFAULT_ALL)
//                .setContentIntent(pendingIntent)
////                .setChannelId("id1")
////                .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
//                .setPriority(android.app.Notification.PRIORITY_HIGH)
////                .setCategory(NotificationCompat.CATEGORY_CALL)
//                .build();
//
//        // Cancel the notification after its selected
//        notification.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
//        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(NotificationsFragment.NUM++, notification);
    }
}
