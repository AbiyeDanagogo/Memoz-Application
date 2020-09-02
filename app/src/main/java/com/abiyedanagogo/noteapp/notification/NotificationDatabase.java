package com.abiyedanagogo.noteapp.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NotificationDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION1 = 2;
    public static final String DATABASE_NAME1 = "notificationdb";
    public static final String DATABASE_TABLE1 = "notificationtable";

    public static final String key_id = "ID";
    public static final String name = "NAME";
    public static final String year = "YEAR";
    public static final String month = "MONTH";
    public static final String day = "DAY";
    public static final String hour = "HOUR";
    public static final String minute = "MINUTE";
    public static final String alarmcheck = "ALARM";

    public NotificationDatabase(Context context) {
        super(context, DATABASE_NAME1, null, DATABASE_VERSION1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + DATABASE_TABLE1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, YEAR INTEGER, MONTH INTEGER, DAY INTEGER, HOUR INTEGER, MINUTE INTEGER, ALARM INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE1);
        onCreate(db);

    }

    public long addNotification(NotificationClass notificationClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(name, notificationClass.getName());
        c.put(year, notificationClass.getYear());
        c.put(month, notificationClass.getMonth());
        c.put(day, notificationClass.getDay());
        c.put(hour, notificationClass.getHour());
        c.put(minute, notificationClass.getMinute());


        long ID = db.insert(DATABASE_TABLE1, null, c);
        Log.d("Inserted", "ID ->" + ID);
        return ID;
    }


    public List<NotificationClass> getNotifications() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<NotificationClass> allNotes = new ArrayList<>();
        // select (all = *) from databaseName
        String query = "SELECT * FROM " + DATABASE_TABLE1;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                NotificationClass note = new NotificationClass();
                note.setID(cursor.getInt(0));
                note.setName(cursor.getString(1));
                note.setYear(cursor.getInt(2));
                note.setMonth(cursor.getInt(3));
                note.setDay(cursor.getInt(4));
                note.setHour(cursor.getInt(5));
                note.setMinute(cursor.getInt(6));
                allNotes.add(note);
            } while (cursor.moveToNext());
        }

        return allNotes;
    }

    public NotificationClass getNotification(long id) {
        // select * from databaseTable whwere id=
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE1, new String[]{key_id, name, year, month, day, hour, minute}, key_id + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new NotificationClass(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
    }


    public Integer deleteNotification(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DATABASE_TABLE1, "ID = ?", new String[]{id});
    }

    public boolean updateNotification(String id, NotificationClass notificationClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(key_id, id);
        contentValues.put(name, notificationClass.getName());
        contentValues.put(year, notificationClass.getYear());
        contentValues.put(month, notificationClass.getMonth());
        contentValues.put(day, notificationClass.getDay());
        contentValues.put(hour, notificationClass.getHour());
        contentValues.put(minute, notificationClass.getMinute());

        db.update(DATABASE_TABLE1, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public boolean setAlarmCheck(String id, int check) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(key_id, id);
        contentValues.put(alarmcheck, check);

        db.update(DATABASE_TABLE1, contentValues, "ID = ?", new String[]{id});
        return true;
    }


    public int getAlarmCheck(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE1, new String[]{alarmcheck}, key_id + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(0);
    }

}
