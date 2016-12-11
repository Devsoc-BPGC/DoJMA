package com.csatimes.dojma.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * GazetteItem contains title,url,date
 */
@IgnoreExtraProperties
public class GazetteItem {

    private String title;
    private String url;
    private String date;

    public GazetteItem() {
        this.title = "";
        this.url = "";
        this.date = "";
    }

    public GazetteItem(String title, String url, String date) {
        this.title = title;
        this.url = url;
        this.date = date;
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
}
