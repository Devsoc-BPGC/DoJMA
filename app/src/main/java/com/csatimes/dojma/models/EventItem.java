package com.csatimes.dojma.models;

import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Event data object that has a title,startDate,start startTime,end startTime,location,desc,id and some more useful methods and variables
 */

public class EventItem extends RealmObject {


    //TODO Add @Index annotation for fields that will be use in searching

    @Exclude
    private boolean alarm = false;
    @PrimaryKey
    private String key;
    @Required
    private String title;
    @Required
    private String startDate;
    @Required
    private String desc;
    private String startTime;
    private String location;
    @Exclude
    private long time = 0;

    public EventItem() {
        desc = "";
        startDate = "";
        startTime = "";
        location = "";
        title = "";
        key = "";
        time = 0;
    }

    public EventItem(String title, String startDate, String startTime, String location, String desc, String key, long time) {
        this.title = title;
        this.desc = desc;
        this.startDate = startDate;
        this.startTime = startTime;
        this.location = location;
        this.key = key;
        this.time = time;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTime() {
        if (getStartDateObj() != null) {
            if (Calendar.getInstance(Locale.ENGLISH).getTime().getTime() < getStartDateObj().getTime())
                return getStartDateObj().getTime();
            else return Long.MAX_VALUE;

        } else return 0;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Exclude
    public Date getStartDateObj() {
        String dtStart = getStartDate() + getStartTime();
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm", Locale.ENGLISH);
        try {
            date = format.parse(dtStart);
        } catch (Exception e) {
            DHC.log("Date parse error in start dateTime " + dtStart + e.getMessage());
        }
        return date;
    }

    @Exclude
    public String getStartDateFormatted() {
        String dateString;
        Date date = getStartDateObj();
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
            try {
                dateString = sdf.format(date);
                return dateString;
            } catch (Exception e) {
                DHC.log("Date parse error in getStartDateFormatted");
            }
        }
        return getStartDate();
    }

    @Exclude
    public String getStartTimeFormatted() {
        String timeString;
        Date date = getStartDateObj();
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
            try {
                timeString = sdf.format(date);
                return timeString;
            } catch (Exception e) {
                DHC.log("Date parse error in getStartTimeFormatted");
            }
        }
        return getStartTime();
    }


    public boolean isAlarmSet() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

}
