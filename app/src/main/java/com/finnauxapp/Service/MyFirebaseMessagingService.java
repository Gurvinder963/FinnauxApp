package com.finnauxapp.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;


import androidx.core.app.NotificationCompat;

import com.finnauxapp.Activities.SplashActivity;
import com.finnauxapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {

        String message = remoteMessage.getData().get("message");
        if (message == null) {
            message = remoteMessage.getNotification().getBody();
        }
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sendNotificationLiveClass(intent, message);
    }

    private void sendNotification(Intent intent, String message) {


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

           // String channelId = getString(R.string.notification_channel_id);
           // String channelName = getString(R.string.app_name);
       //     int importance = NotificationManager.IMPORTANCE_HIGH;

        /*    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }*/
            Bitmap icon= BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_notification);


            int num = (int) System.currentTimeMillis();



                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setLargeIcon(icon)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(unescapeJavaString(message))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(unescapeJavaString(message)))
                        .setAutoCancel(true)
                        .setSound(alarmSound)
                        .setContentIntent(pendingIntent);


                notificationManager.notify(num, notificationBuilder.build());




    }

    private void sendNotificationLiveClass(Intent intent, String message) {


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            String channelId = getString(R.string.notification_channel_id);
            String channelName = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            Bitmap icon=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.ic_icon);


            int num = (int) System.currentTimeMillis();


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(icon)
                    .setContentTitle(getString(R.string.app_name))
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(unescapeJavaString(message)))
                    .setAutoCancel(true)
                    .setContentText(unescapeJavaString(message))
                    .setSound(alarmSound)
                    .setContentIntent(pendingIntent);

            PendingIntent pIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

            notificationManager.notify(num, notificationBuilder.build());



    }
    public String unescapeJavaString(String st) {

        StringBuilder sb = new StringBuilder(st.length());

        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == st.length() - 1) ? '\\' : st
                        .charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                            && st.charAt(i + 1) <= '7') {
                        code += st.charAt(i + 1);
                        i++;
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                                && st.charAt(i + 1) <= '7') {
                            code += st.charAt(i + 1);
                            i++;
                        }
                    }
                    sb.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= st.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt(
                                "" + st.charAt(i + 2) + st.charAt(i + 3)
                                        + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        sb.append(Character.toChars(code));
                        i += 5;
                        continue;
                }
                i++;
            }
            sb.append(ch);
        }
        return sb.toString();
    }
}
