package com.csatimes.dojma.models;

import io.realm.RealmObject;

/**
 * PosterItem containing title and url field
 */

public class PosterItem extends RealmObject {
    private String title;
    private String url;

    public PosterItem(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public PosterItem() {
        title = "";
        url = "";
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
}
