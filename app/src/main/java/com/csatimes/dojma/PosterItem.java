package com.csatimes.dojma;

/**
 * Created by Vikramaditya Kukreja on 04-08-2016.
 */

public class PosterItem {
    String title;
    String url;

    public PosterItem(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public PosterItem() {
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
