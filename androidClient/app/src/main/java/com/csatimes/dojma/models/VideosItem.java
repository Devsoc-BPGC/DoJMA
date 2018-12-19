package com.csatimes.dojma.models;

public class VideosItem {
    private String videoURL,videoName,dateStamp,creator,type,description;

    public VideosItem(String videoURL,String videoName,String dateStamp,String creator,String type,String description)
    {
        this.videoURL=videoURL;
        this.videoName=videoName;
        this.dateStamp=dateStamp;
        this.creator=creator;
        this.type=type;
        this.description=description;
    }

    public String getVideoURL()
    {
        return videoURL;
    }
    public String getVideoName(){return videoName;}
    public String getDateStamp(){return dateStamp;}
    public String getType() { return type; }
    public String getCreator() { return creator; }
    public String getDescription() { return description; }

    public void setvideoURL(String videoURL)
    {
        this.videoURL = videoURL;
    }

    public void setVideoName(String videoName)
    {
        this.videoName = videoName;
    }

    public void setDateStamp(String dateStamp)
    {
        this.dateStamp = dateStamp;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
