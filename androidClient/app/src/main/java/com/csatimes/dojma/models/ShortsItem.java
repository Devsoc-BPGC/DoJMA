package com.csatimes.dojma.models;

import java.sql.Timestamp;

public class ShortsItem {
    public String title;
    public String subtitle;
    public String timestamp;
    public String imageUrl;

    public ShortsItem(){
        //Default Constructor
    }

    public ShortsItem(String title, String subtitle, String imageUrl, String timestamp) {
        this.title = title;
        this.subtitle = subtitle;
        this.timestamp = timestamp;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
