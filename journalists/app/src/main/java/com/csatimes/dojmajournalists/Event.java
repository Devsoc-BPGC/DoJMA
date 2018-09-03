package com.csatimes.dojmajournalists;

/**
 * @author Rushikesh Jogdand.
 */
public class Event {
    public String title;
    public String desc;
    public String startTime;
    public String startDate;
    public String location;

    public Event(final String title, final String desc, final String startTime, final String startDate, final String location) {
        this.title = title;
        this.desc = desc;
        this.startTime = startTime;
        this.startDate = startDate;
        this.location = location;
    }
    public Event(final String title){
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }
}
