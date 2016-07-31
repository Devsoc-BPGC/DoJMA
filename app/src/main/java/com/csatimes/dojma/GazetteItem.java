package com.csatimes.dojma;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

class GazetteItem {

    String title;
    String url;

    GazetteItem() {
        this.title = "";
        this.url = "";
    }

    GazetteItem(String title, String url) {
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
