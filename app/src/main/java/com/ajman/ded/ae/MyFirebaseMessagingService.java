package com.ajman.ded.ae;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Locale;
import java.util.Random;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String channelId = "1";
    String channel2 = "2";
    private NotificationManager notificationManager;

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Setting up Notification channels for android O and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels();
        }
        long when = System.currentTimeMillis();
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentView.setTextViewText(R.id.notification_no, String.format("%s %s", getString(R.string.notification_no), remoteMessage.getData().get("RequestNumber"), Locale.forLanguageTag("ar")));
        contentView.setTextViewText(R.id.content, String.format("%s %s %s", remoteMessage.getData().get("message") ,getString(R.string.notification_content),remoteMessage.getData().get("EstablishmentNameAR"), Locale.forLanguageTag("ar")));
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(contentView)
                .setCustomContentView(contentView)
                .setCustomHeadsUpContentView(contentView)
                .setAutoCancel(true)
                .setWhen(when)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels() {

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(channel2, "DedEye", NotificationManager.IMPORTANCE_HIGH);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
