package com.abiyedanagogo.noteapp.notes;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Created by Abiye Danagogo on 20/04/2020.
 * This class defines a note and gives it attributes and properties
 * The properties of notes are an ID, title, content, date created, time created
 * */

public class Note implements Parcelable {
    private Long ID;
    private String title;
    private String content;
    private String date;
    private String time;

    Note() {
    }

    Note(String title, String content, String date, String time) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;

    }

    Note(long id, String title, String content, String date, String time) {
        this.ID = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    protected Note(Parcel in) {
        if (in.readByte() == 0) {
            ID = null;
        } else {
            ID = in.readLong();
        }
        title = in.readString();
        content = in.readString();
        date = in.readString();
        time = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (ID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(ID);
        }
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(date);
        dest.writeString(time);
    }
}
