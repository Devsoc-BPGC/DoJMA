package com.csatimes.dojma;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class GazetteItem {

    String title;
    String link;

    public GazetteItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
