package com.csatimes.dojmajournalists;

/**
 * @author Aryan Agarwal.
 */

public class EventModel {
    public String title;
    public String desc;
    public String startTime;
    public String startDate;
    public String location;

    public EventModel(final String title, final String desc, final String startTime, final String startDate, final String location) {
        this.title = title;
        this.desc = desc;
        this.startTime = startTime;
        this.startDate = startDate;
        this.location = location;
    }

    public void EventsModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
