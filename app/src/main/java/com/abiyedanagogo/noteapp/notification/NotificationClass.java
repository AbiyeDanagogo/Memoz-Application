package com.abiyedanagogo.noteapp.notification;

/*
 * Created by Abiye Danagogo on 20/04/2020.
 * This class creates the properties and funmctions of a notification.
 * Each notification has a year, month, day, hour and minute for the specific time it will ring and each also has a name
 * */

public class NotificationClass {
    private int ID;
    private String name;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    NotificationClass() {
    }

    NotificationClass(String name, int year, int month, int day, int hour, int minute) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    NotificationClass(int id, String name, int year, int month, int day, int hour, int minute) {
        this.ID = id;
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

}
