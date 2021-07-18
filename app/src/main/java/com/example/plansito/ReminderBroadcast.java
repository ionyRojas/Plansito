package com.example.plansito;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {   
    //Creates notification based on data received
    @Override
    public void onReceive(Context context, Intent intent) {
        //Receives and sets data
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyPlancito")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("You created a new Plancito task")
                .setContentText("Your task been store in the database")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
