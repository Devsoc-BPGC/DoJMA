package com.csatimes.dojma;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */
@IgnoreExtraProperties
class GazetteItem {

    String title;
    String url;
    String date;

    GazetteItem() {
        this.title = "";
        this.url = "";
        this.date = "";
    }

    GazetteItem(String title, String url, String date) {
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
