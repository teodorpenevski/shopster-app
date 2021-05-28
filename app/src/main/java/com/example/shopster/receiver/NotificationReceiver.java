package com.example.shopster.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.shopster.service.NotificationService;

public class NotificationReceiver extends BroadcastReceiver {

    private static final int NOTIFICATIONS_INTERVAL_IN_SECONDS = 15;

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, NotificationService.class));
    }
}
