package com.ajman.ded.ae;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String channelId = "1";
    private String requestNo, message, establishment, notificationId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            try {
                requestNo = remoteMessage.getData().get("RequestNumber");
                message = remoteMessage.getData().get("message");
                establishment = remoteMessage.getData().get("EstablishmentNameAR");
                notificationId = remoteMessage.getData().get("notificationId");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(getString(R.string.notification_channel_id), "DedEye", NotificationManager.IMPORTANCE_DEFAULT);
                    mChannel.enableLights(true);
                    mChannel.setLightColor(Color.RED);
                    mChannel.enableVibration(true);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(
                            NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(mChannel);
                }

                Intent intent = new Intent(getApplicationContext(), NotificationDetailsActivity.class);
                intent.putExtra("id", String.valueOf(notificationId));

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                long when = System.currentTimeMillis();
                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
                contentView.setTextViewText(R.id.notification_no, String.format("%s %s", "رقم البلاغ", requestNo));
                contentView.setTextViewText(R.id.content, String.format("%s %s %s", message, "فى المنشأة:", establishment));
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Notification notification = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setCustomContentView(contentView)
                        .setCustomBigContentView(contentView)
                        .setContent(contentView)
                        .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                        .setAutoCancel(true)
                        .setWhen(when)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .build();

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0, notification);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

