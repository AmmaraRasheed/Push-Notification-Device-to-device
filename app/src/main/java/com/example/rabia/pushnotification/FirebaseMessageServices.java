package com.example.rabia.pushnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessageServices extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String messageTitle=remoteMessage.getNotification().getTitle();
        String messageBody=remoteMessage.getNotification().getBody();
        String click_action=remoteMessage.getNotification().getClickAction();
        String  dataMessage=remoteMessage.getData().get("message");
        String  datafrom=remoteMessage.getData().get("from_email");
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,getString(R.string.default_notification_channel_id))
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(messageTitle);


        Intent resultIntent=new Intent(click_action);
        resultIntent.putExtra("message",dataMessage);
        resultIntent.putExtra("from_email",datafrom);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        int notificationId=(int)System.currentTimeMillis();
        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(notificationId,builder.build());
    }
}
