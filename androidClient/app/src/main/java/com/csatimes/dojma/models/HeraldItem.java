package com.csatimes.dojma.models;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;

/**
 * HeraldItemFormat
 */

@SuppressWarnings({"FieldNamingConvention", "DuplicateStringLiteralInspection"})
public class HeraldItem extends RealmObject {

    @Ignore
    public static final String CATEGORY = "category";
    public static final String FAV = "fav";
    private static final String TAG = TAG_PREFIX + HeraldItem.class.getName();
    @PrimaryKey
    public String postID;
    public String title;
    public String title_plain;
    public String originalDate;
    public String originalTime;
    public String updateDate;
    public String updateTime;
    public String thumbnailUrl;
    public String url;
    public String content;
    public String excerpt;
    @Index
    public String category;
    public boolean fav;

    @Exclude
    @Ignore
    private String formattedDate;

    /**
     * We can get low certain quality version of thumbnails by adding `-wxh` in
     * url. Beware this works only for certain dimensions.
     * @param url of *original* image
     */
    public static String getSmallImage(String url, int width, int height) {
        if (TextUtils.isEmpty(url) || !url.contains(".")) {
            Log.e(TAG, String.format("getSmallImage: improper url %s", url));
            return null;
        }
        int lastDot = url.lastIndexOf('.');
        String prefix = url.substring(0, lastDot);
        String suffix = url.substring(lastDot);
        return String.format(Locale.ENGLISH, "%s-%dx%d%s", prefix, width, height, suffix);
    }

    @Exclude
    public String getFormattedDate() {
        return formattedDate == null ? getOriginalDate() : formattedDate;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(String originalDate) {
        this.originalDate = originalDate;
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        Date of;
        try {
            of = simpleDate.parse(originalDate);
        } catch (ParseException e) {
            formattedDate = null;
            return;
        }
        SimpleDateFormat tf = new SimpleDateFormat("dd MMM , ''yy", Locale.UK);
        formattedDate = tf.format(of);
    }
}
