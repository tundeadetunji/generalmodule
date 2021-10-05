package com.inovationware.generalmodule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Feedback {
    Context context;

    public Feedback(Context context) {
        this.context = context;
    }

    public void feedback(String string){
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    public void feedback(String string, int Toast_Length){
        Toast.makeText(context, string, Toast_Length).show();
    }

    public void Inform(String title, String content, String bigText, int Notification_Compat_Priority,  int Small_Icon_Drawable_Resource, Class<?> Activity_Class, int notificationId, String CHANNEL_ID, String channel_name, String channel_description){
        createNotificationChannel(CHANNEL_ID, channel_name, channel_description);

        Intent intent = new Intent(context, Activity_Class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(Small_Icon_Drawable_Resource)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(Notification_Compat_Priority)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(bigText))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel(String CHANNEL_ID, String channel_name, String channel_description) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channel_name;
            String description = channel_description;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
