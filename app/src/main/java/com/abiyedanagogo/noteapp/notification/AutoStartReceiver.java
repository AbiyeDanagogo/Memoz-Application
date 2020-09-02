package com.abiyedanagogo.noteapp.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.List;

public class AutoStartReceiver extends BroadcastReceiver {

    List<GroupNotification> groupNotifications;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationDatabase db = new NotificationDatabase(context);


        groupNotifications = db.getNotifications();


        for (GroupNotification notification : groupNotifications) {
            int id = notification.getID();
            String message = notification.getName();
            int year = notification.getYear();
            int month = notification.getMonth();
            int day = notification.getDay();
            int hour = notification.getHour();
            int minute = notification.getMinute();

            //Log.d("thisisoneofmylogs" + id, "onReceive: "+year +" "+month+" "+day+" "+hour+" "+minute);

            if (db.getAlarmCheck(id) != 1) {
                Calendar c = Calendar.getInstance();

                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent1 = new Intent(context, AlertReceiver.class);
                intent1.putExtra("message", message);
                intent1.putExtra("alarmid", id);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            }

        }


    }
}
