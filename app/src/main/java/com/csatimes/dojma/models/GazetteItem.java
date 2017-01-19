package com.csatimes.dojma.models;

import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * GazetteItem contains title,url,dateTime
 */
@IgnoreExtraProperties
public class GazetteItem extends RealmObject {

    private String title;
    private String url;
    private String date;
    private String imageUrl;
    @Exclude
    private long time = 0;
    @PrimaryKey
    private String key;

    public GazetteItem() {
        this.title = "";
        this.url = "";
        this.date = "";
        this.imageUrl = "";
        this.time = Long.MAX_VALUE;
    }

    public GazetteItem(String title, String url, String date, String imageUrl, long time, String key) {
        this.title = title;
        this.url = url;
        this.date = date;
        this.imageUrl = imageUrl;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTime() {
        if (getDateObj() != null)
            return getDateObj().getTime();
        else return 0;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Exclude
    public Date getDateObj() {
        String releaseDateString = date;
        Date dateObj = null;
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
        try {
            dateObj = format.parse(releaseDateString);
        } catch (Exception e) {
            DHC.log("Date parse error in gazette dateTime " + releaseDateString + e.getMessage());
        }
        return dateObj;
    }

    @Exclude
    public String getReleaseDateFormatted() {
        String dateString;
        Date dateObj = getDateObj();
        if (dateObj != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
            try {
                dateString = sdf.format(dateObj);
                return dateString;
            } catch (Exception e) {
                DHC.log("Date parse error in gaztte formatted dateTime string");
            }
        }
        return date;
    }
}
