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
    private String postID;
    private String title;
    private String title_plain;
    private String originalDate;
    private String originalTime;
    private String updateDate;
    private String updateTime;
    private String thumbnailUrl;
    private String url;
    private String content;
    private String excerpt;
    @Index
    private String category;
    private boolean fav;

    @Exclude
    @Ignore
    private String formattedDate;

    public HeraldItem() {
        title = "";
        title_plain = "";
        originalDate = "";
        updateDate = "";
        originalTime = "";
        updateTime = "";
        thumbnailUrl = "";
        url = "";
        content = "";
        excerpt = "";
        category = "";
        fav = false;
    }

    public HeraldItem(String postID, String title, String title_plain, String originalDate, String originalMonthYear,
                      String updateDate, String originalTime, String updateTime,
                      String thumbnailUrl, String url, String content,
                      String excerpt, String authorName,
                      String category,
                      boolean fav) {
        this.postID = postID;
        this.title = title;
        this.title_plain = title_plain;
        this.originalDate = originalDate;
        this.updateDate = updateDate;
        this.originalTime = originalTime;
        this.updateTime = updateTime;
        this.thumbnailUrl = thumbnailUrl;
        this.url = url;
        this.content = content;
        this.excerpt = excerpt;
        this.category = category;
        this.fav = fav;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_plain() {
        return title_plain;
    }

    public void setTitle_plain(String title_plain) {
        this.title_plain = title_plain;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(String originalDate) {
        this.originalDate = originalDate;
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        Date of = null;
        try {
            of = simpleDate.parse(originalDate);
        } catch (ParseException e) {
            formattedDate = null;
            return;
        }
        SimpleDateFormat tf = new SimpleDateFormat("dd MMM , ''yy", Locale.UK);
        formattedDate = tf.format(of);
    }


    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getOriginalTime() {
        return originalTime;
    }

    public void setOriginalTime(String originalTime) {
        this.originalTime = originalTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    @Exclude
    public String getFormattedDate() {
        return formattedDate == null ? getOriginalDate() : formattedDate;
    }
}
