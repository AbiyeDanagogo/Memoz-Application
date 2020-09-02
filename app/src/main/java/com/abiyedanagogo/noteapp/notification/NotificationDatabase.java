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

    public long addNotification(GroupNotification groupNotification) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(name, groupNotification.getName());
        c.put(year, groupNotification.getYear());
        c.put(month, groupNotification.getMonth());
        c.put(day, groupNotification.getDay());
        c.put(hour, groupNotification.getHour());
        c.put(minute, groupNotification.getMinute());


        long ID = db.insert(DATABASE_TABLE1, null, c);
        Log.d("Inserted", "ID ->" + ID);
        return ID;
    }


    public List<GroupNotification> getNotifications() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<GroupNotification> allNotes = new ArrayList<>();
        // select (all = *) from databaseName
        String query = "SELECT * FROM " + DATABASE_TABLE1;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                GroupNotification note = new GroupNotification();
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

    public GroupNotification getNotification(long id) {
        // select * from databaseTable whwere id=
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE1, new String[]{key_id, name, year, month, day, hour, minute}, key_id + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new GroupNotification(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
    }


    public Integer deleteNotification(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DATABASE_TABLE1, "ID = ?", new String[]{id});
    }

    public boolean updateNotification(String id, GroupNotification groupNotification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(key_id, id);
        contentValues.put(name, groupNotification.getName());
        contentValues.put(year, groupNotification.getYear());
        contentValues.put(month, groupNotification.getMonth());
        contentValues.put(day, groupNotification.getDay());
        contentValues.put(hour, groupNotification.getHour());
        contentValues.put(minute, groupNotification.getMinute());

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
