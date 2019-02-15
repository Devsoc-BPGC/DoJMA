package com.csatimes.dojma.models;

public class VideosItem {
    public String videoURL;
    public String videoName;
    public String dateStamp;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String creator;
    public String type;
    public String description;
    // in case of youtube video
    public String id;
    public String thumbUrl;
}
