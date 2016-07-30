package com.csatimes.dojma;

/**
 * Event data object that has a title,startDate,start startTime,end startTime,location,desc
 */

class EventItem {
    String title;
    private String desc;
    private String startDate;
    private String startTime;
    private String endTime;
    private String endDate;
    private String location;

    public EventItem() {
        desc = null;
        startDate = null;
        startTime = null;
        endTime = null;
        endDate = null;
        location = null;
        title = null;
    }

    public EventItem(String title, String startDate, String startTime, String endTime, String location, String desc, String endDate) {
        this.title = title;
        this.desc = desc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
