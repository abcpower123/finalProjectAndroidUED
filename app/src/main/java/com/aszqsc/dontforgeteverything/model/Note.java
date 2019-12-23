package com.aszqsc.dontforgeteverything.model;

import com.aszqsc.dontforgeteverything.R;

import java.io.Serializable;
import java.util.Calendar;

public class Note implements Serializable {
    private int id;
    private int cateID; //1: works, 2:family,3:friends
    private int colorTheme;
    private String title;
    private String content;
    private boolean isNoti;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String password;

    public Note(int id, int cateID, int colorTheme, String title, String content, boolean isNoti, int year, int month, int day, int hour, int minute, String password) {
        this.id = id;
        this.cateID = cateID;
        this.colorTheme = colorTheme;
        this.title = title;
        this.content = content;
        this.isNoti = isNoti;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.password = password;
    }

    public Note() {
        colorTheme = R.color.note_c1;
        password = "";
        isNoti = true;
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        cateID = 1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCateID() {
        return cateID;
    }

    public void setCateID(int cateID) {
        this.cateID = cateID;
    }

    public int getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(int colorTheme) {
        this.colorTheme = colorTheme;
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

    public boolean isNoti() {
        return isNoti;
    }

    public void setNoti(boolean noti) {
        isNoti = noti;
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

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", cateID=" + cateID +
                ", colorTheme=" + colorTheme +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isNoti=" + isNoti +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", password='" + password + '\'' +
                '}';
    }
}
