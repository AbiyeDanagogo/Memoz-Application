package com.abiyedanagogo.noteapp.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.abiyedanagogo.noteapp.FirstActivity;
import com.abiyedanagogo.noteapp.R;

import static com.abiyedanagogo.noteapp.notification.App.CHANNEL_1_ID;

/*
 * Created by Abiye Danagogo on 20/04/2020.
 * This class extends service and it is what allows our application to start the ringtone and vibrate even if the
 * application is closed by creating a foreground service when it recieves an alert from the alert receiver.
 * */

public class RingtoneService extends Service {

    private Ringtone ringtone;
    private Vibrator vibrator;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String message = intent.getStringExtra("message");
        int id = intent.getIntExtra("ID", 1);

        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Intent firstActivityIntent = new Intent(this, FirstActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(FirstActivity.class);
        stackBuilder.addNextIntent(firstActivityIntent);

        Intent notificationUpdateIntent = new Intent(this, UpdateNotificationActivity.class);
        notificationUpdateIntent.putExtra("ID", id);
        stackBuilder.addNextIntent(notificationUpdateIntent);


        PendingIntent pendingIntent = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setContentTitle("Reminder")
                .setContentText(message)
                .setSmallIcon(R.drawable.noteappicon)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);


        this.ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        ringtone.play();
        vibrator.vibrate(10000);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
        vibrator.cancel();
    }

}
