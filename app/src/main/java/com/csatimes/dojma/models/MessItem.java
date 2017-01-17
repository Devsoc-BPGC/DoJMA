package com.csatimes.dojma.models;

import io.realm.RealmObject;

/**
 * Created by vikramaditya on 6/1/17.
 */

public class MessItem extends RealmObject {
    private String imageUrl;
    private String title;

    public MessItem() {
        imageUrl = "";
        title = "";
    }

    public MessItem(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
