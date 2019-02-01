package com.csatimes.dojma.models;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * HeraldItemFormat
 */

@SuppressWarnings({"FieldNamingConvention", "DuplicateStringLiteralInspection"})
public class HeraldItem extends RealmObject {

    @Ignore
    public static final String CATEGORY = "category";
    public static final String FAV = "fav";

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

    @Exclude
    public String getFormattedDate() {
        return formattedDate == null ? getOriginalDate() : formattedDate;
    }
}
