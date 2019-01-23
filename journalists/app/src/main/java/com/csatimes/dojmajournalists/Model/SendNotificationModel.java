package com.csatimes.dojmajournalists.Model;

public class SendNotificationModel {

    public String title;
    public String subtitle;
    public String timestamp;
    public String imageUrl;
    public String clickUrl;

    public SendNotificationModel(String title, String subtitle, String timestamp, String imageUrl, String clickUrl){
        this.title = title;
        this.subtitle = subtitle;
        this.timestamp = timestamp;
        this.imageUrl = imageUrl;
        this.clickUrl = clickUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }




}
