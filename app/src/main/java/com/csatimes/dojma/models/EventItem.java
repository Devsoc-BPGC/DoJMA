package com.csatimes.dojma.models;

import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Event data object that has a title,startDate,start startTime,end startTime,location,desc
 */

public class EventItem extends RealmObject {

    private String title;
    private String desc;
    private String startDate;
    private String startTime;
    private String endTime;
    private String endDate;
    private String location;

    @Exclude
    @Ignore
    private Date startDateObj;
    @Exclude
    @Ignore
    private Date endDateObj;

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

    @Exclude
    public Date getStartDateObj() {
        String dtStart = getStartDate() + getStartTime();
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm", Locale.ENGLISH);
        try {
            date = format.parse(dtStart);
        } catch (Exception e) {
            DHC.log("Date parse error");
        }
        return date;
    }

    @Exclude
    public Date getEndDateObj() {
        Date date = null;
        if (getEndDate() != null && getEndTime() != null) {
            String foo = getEndDate() + getEndTime();
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm", Locale.ENGLISH);
            try {
                date = format.parse(foo);
            } catch (Exception e) {
                DHC.log("Date parse error");
            }
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
                DHC.log("Date parse error in getStartDateFormatted" );
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
                DHC.log("Date parse error in getStartTimeFormatted" );
            }
        }
        return getStartTime();
    }
}
