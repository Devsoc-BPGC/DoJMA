package com.csatimes.dojma.models;

import android.util.Log;

import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Event data object that has a title,startDate,start startTime,end startTime,
 * location,desc,id and some more useful methods and variables
 */

public class EventItem extends RealmObject {

    public static final String TAG = EventItem.class.getSimpleName();
    @Ignore
    public static final String FIELD_ORIGINAL_DATE = "originalDate";

    @Ignore
    public static final String FIELD_TITLE = "title";

    @Ignore
    public static final String FIELD_LOCATION = "location";

    @Ignore
    public static final String FIELD_DESC = "desc";

    @PrimaryKey
    private String key;

    @Index
    @Required
    private String title;

    @Required
    private String startDate;

    @Required
    private String startTime;

    @Index
    private String desc;

    @Index
    private String location;

    @Exclude
    private long time;

    @Nullable
    @Exclude
    private String startDateFormatted;

    @Nullable
    @Exclude
    private String startTimeFormatted;

    @Exclude
    private Date startDateObj;

    public EventItem() {
        desc = "";
        startDate = "";
        startTime = "";
        location = "";
        title = "";
        key = "";
        time = Long.MAX_VALUE;
        startDateFormatted = "";
        startTimeFormatted = "";
    }

    public EventItem(final String title, final String startDate,
                     final String startTime, final String location,
                     final String desc, final String key, final long time) {
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

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    @SuppressWarnings("FeatureEnvy")
    public void setDateTime(final String datetime) {
        try {
            this.startDate = datetime.substring(0, 8);
            this.startTime = datetime.substring(8);
        } catch (final Exception e) {
            this.startDate = "01012000";
            this.startTime = "0000";
        }

        final Date date;
        //noinspection HardCodedStringLiteral
        final SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm", Locale.ENGLISH);

        try {
            date = format.parse(datetime);
        } catch (final Exception e) {
            //noinspection HardCodedStringLiteral,StringConcatenation
            Log.d(TAG, String.format("setDateTime: Date parse error %s%s", datetime, e.getMessage()));
            this.time = Long.MAX_VALUE;
            startDateFormatted = null;
            startTimeFormatted = null;
            return;
        }
        startDateObj = date;

        /*
         * Set {@link EventItem#startDateFormatted}
         */
        //noinspection HardCodedStringLiteral
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
        try {
            startDateFormatted = sdf.format(date);
        } catch (final Exception e) {
            Log.e(TAG, String.format("setDateTime: parse error %s", datetime)); //NON-NLS
            startDateFormatted = null;
        }

        /*
         * Set {@link EventItem#startTimeFormatted}
         */
        //noinspection HardCodedStringLiteral
        sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        try {
            startTimeFormatted = sdf.format(date);
        } catch (final Exception e) {
            //noinspection HardCodedStringLiteral
            DHC.e(TAG, "h:mm a parse error");
            startTimeFormatted = null;
        }

        /*
         * Set {@link EventItem#time} which is used for sorting in
         * {@link com.csatimes.dojma.fragments.EventsFragment} fragment
         */
        final long currentTime = Calendar.getInstance(Locale.ENGLISH).getTime().getTime();
        this.time = currentTime < date.getTime() ? date.getTime() : Long.MAX_VALUE;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public long getTime() {
        return time;
    }

    @SuppressWarnings("unused")
    private void setTime(final long time) {
        this.time = time;
    }

    public Date getStartDateObj() {
        return startDateObj;
    }

    public String getStartDate() {
        return startDate;
    }

    @SuppressWarnings("unused")
    private void setStartDate(final String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    @SuppressWarnings("unused")
    private void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getStartDateFormatted() {
        return startDateFormatted == null ? startDate : startDateFormatted;
    }

    public String getStartTimeFormatted() {
        return startTimeFormatted == null ? startTime : startTimeFormatted;
    }

}
