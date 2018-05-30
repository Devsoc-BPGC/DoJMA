package com.csatimes.dojma.models;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class LinkItem extends RealmObject {

    @Ignore
    public static final String FIELD_TITLE = "title";
    private String title;
    private String url;

    public LinkItem() {
        this.title = "";
        this.url = "";
    }

    public LinkItem(String title, String url) {
        this.title = title;
        this.url = url;
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