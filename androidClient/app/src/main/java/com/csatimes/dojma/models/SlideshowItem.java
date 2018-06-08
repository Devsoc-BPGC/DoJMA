package com.csatimes.dojma.models;

import io.realm.RealmObject;

/**
 * Created by vikramaditya on 27/2/17.
 */

public class SlideshowItem extends RealmObject {
    private String imageUrl;
    private String title;
    private String subTitle;

    public SlideshowItem() {
        imageUrl = "";
        title = "";
        subTitle = "";
    }

    public SlideshowItem(String imageUrl, String title, String subTitle) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.subTitle = subTitle;
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
