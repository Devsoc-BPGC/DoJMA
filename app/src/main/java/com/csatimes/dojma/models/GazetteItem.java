package com.csatimes.dojma.models;

import com.google.firebase.database.IgnoreExtraProperties;

import io.realm.RealmObject;

/**
 * GazetteItem contains title,url,date
 */
@IgnoreExtraProperties
public class GazetteItem extends RealmObject {

    private String title;
    private String url;
    private String date;
    private String imageUrl;

    public GazetteItem() {
        this.title = "";
        this.url = "";
        this.date = "";
        this.imageUrl = "";
    }

    public GazetteItem(String title, String url, String date,String imageUrl) {
        this.title = title;
        this.url = url;
        this.date = date;
        this.imageUrl = imageUrl;
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
}
