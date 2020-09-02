package com.abiyedanagogo.noteapp.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        String message = intent.getStringExtra("message");
        int id = intent.getIntExtra("alarmid", 1);

        /*Intent mainIntent = new Intent(context, FirstActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(FirstActivity.class);
        stackBuilder.addNextIntent(mainIntent);

        Intent resultIntent = new Intent(context, NotificationUpdateActivity.class);
        resultIntent.putExtra("ID", id);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.noteappicon)
                .setContentTitle("Reminder")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(resultPendingIntent)
                .setColor(Color.rgb(110, 148, 194))
                .setAutoCancel(true)
                .build();
        notificationManager.notify(id, notification);*/

        NotificationDatabase db = new NotificationDatabase(context);
        db.setAlarmCheck(String.valueOf(id), 1);


        Intent ringIntent = new Intent(context, RingtoneService.class);
        ringIntent.putExtra("message", message);
        ringIntent.putExtra("ID", id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(ringIntent);
        } else {
            context.startService(ringIntent);
        }

    }
}
