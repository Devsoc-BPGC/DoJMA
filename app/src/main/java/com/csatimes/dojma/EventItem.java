package com.csatimes.dojma;

/**
 * Created by Vikramaditya Kukreja on 19-07-2016.
 */

public class EventItem {
    String title;
    String desc;
    String date;
    String time;
    String location;

    public EventItem(String title, String date, String time, String location, String desc) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
