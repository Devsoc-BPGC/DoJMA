package com.csatimes.dojma;

/**
 * Event data object that has a title,date,start time,end time,location,desc
 *
 */

class EventItem {
    String title;
    private String desc;
    private String date;
    private String time;
    private String endTime;
    private String location;

    public EventItem(String title, String date, String time, String endTime, String location, String desc) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.endTime = endTime;
        this.location = location;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
