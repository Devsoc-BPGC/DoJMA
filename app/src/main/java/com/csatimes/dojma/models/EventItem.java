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

    public static final String TAG = "models.EventItem";
    //TODO Add @Index annotation for fields that will be used in searching/ or used in finding distinct items

    @PrimaryKey
    private String key;
    @Required
    private String title;

    @Required
    private String startDate;
    @Required
    private String startTime;
    private String desc;
    private String location;
    @Exclude
    private long time = 0;
    @Exclude
    private String startDateFormatted;
    @Exclude
    private String startTimeFormatted;
    @Exclude
    private Date startDateObj;

    public EventItem() {
        desc = "SET DESC";
        startDate = "01011990";
        startTime = "0000";
        location = "SET LOCATION";
        title = "SET TITLE";
        key = "SET KEY";
        time = Long.MAX_VALUE;
        startDateFormatted = "01 JAN";
        startTimeFormatted = "0:00 am";
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

    public void setDateTime(String datetime) {

        try {
            setStartDate(datetime.substring(0, 8));
            setStartTime(datetime.substring(8));
        } catch (Exception e) {
            setStartDate("01012000");
            setStartTime("0000");
        }

        Date date;
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm", Locale.ENGLISH);

        try {
            date = format.parse(datetime);
            startDateObj = date;

            /**
             * Set {@link EventItem#startDateFormatted}
             */
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
            try {
                startDateFormatted = sdf.format(date);
            } catch (Exception e) {
                DHC.log(TAG, "dd MMM parse error");
                startDateFormatted = null;
            }

            /**
             * Set {@link EventItem#startTimeFormatted}
             */
            sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
            try {
                startTimeFormatted = sdf.format(date);
            } catch (Exception e) {
                DHC.log(TAG, "h:mm a parse error");
                startTimeFormatted = null;
            }

            /**
             * Set {@link EventItem#time} which is used for sorting in {@link com.csatimes.dojma.fragments.Events} fragment
             */
            if (Calendar.getInstance(Locale.ENGLISH).getTime().getTime() < date.getTime())
                setTime(date.getTime());
            else setTime(Long.MAX_VALUE);

        } catch (Exception e) {
            DHC.log("Date parse error in start dateTime " + datetime + e.getMessage());
            //TODO remove stack trace
            e.printStackTrace();
            setTime(Long.MAX_VALUE);
            startDateFormatted = null;
            startTimeFormatted = null;
        }

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
        return time;
    }

    private void setTime(long time) {
        this.time = time;
    }

    public Date getStartDateObj() {
        return startDateObj;
    }

    public String getStartDate() {
        return startDate;
    }

    private void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    private void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDateFormatted() {
        return startDateFormatted == null ? startDate : startDateFormatted;
    }

    public String getStartTimeFormatted() {
        return startTimeFormatted == null ? startTime : startTimeFormatted;
    }

}
