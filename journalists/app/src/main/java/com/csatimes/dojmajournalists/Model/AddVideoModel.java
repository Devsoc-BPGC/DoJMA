package com.csatimes.dojmajournalists.Model;

public class AddVideoModel {
    public String creator;
    public String dateStamp;
    public String description;
    public String type;
    public String videoURL;
    public String videoName;

    public AddVideoModel (String videoName, String description, String dateStamp, String type, String creator, String videoURL){
        this.videoName = videoName;
        this.description = description;
        this.dateStamp = dateStamp;
        this.type = type;
        this.creator = creator;
        this.videoURL = videoURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
