package com.inovationware.generalmodule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Locale;

public class Feedback {
    TextToSpeech tts;

    private Context context;

    public Feedback(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        //can't do it
                    }
                } /*else {

                }*/
            }
        });
    }

    public void ShutDownTTS(){
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    public void toast(String string){
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    public void toast(String string, int Toast_Length){
        Toast.makeText(context, string, Toast_Length).show();
    }

    public void feedback(String message){
        if (message.length() < 1) return;

        if (tts == null){
            tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
                        int result = tts.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            //can't do it
                        }
                    } /*else {

                }*/
                }
            });
        }

        tts.speak(message, TextToSpeech.QUEUE_ADD, null);
        ShutDownTTS();
    }

    public void inform(String title, String content, String ticker, String bigText, int NotificationCompat_Priority,  int Small_Icon_Drawable_Resource,  int Large_Icon_Drawable_Resource, Class<?> Activity_Class, int notificationId, String channel_id, String channel_name, String channel_description){
        createNotificationChannel(channel_id, channel_name, channel_description);

        Intent intent = new Intent(context, Activity_Class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(Small_Icon_Drawable_Resource)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat_Priority)
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())

                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(context.getResources(), Large_Icon_Drawable_Resource)).bigLargeIcon(null))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));

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
