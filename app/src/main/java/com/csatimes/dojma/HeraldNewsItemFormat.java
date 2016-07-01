package com.csatimes.dojma;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class HeraldNewsItemFormat extends RealmObject {
    @PrimaryKey
    @Required
    private String postID;
    private String title;
    private String author;
    private String originalDate;
    private String updateDate;
    private String originalTime;
    private String updateTime;
    private String imageURL;
    private String imageSavedLoc;
    private String link;
    private String desc = null;
    private boolean fav = false;
    private boolean read = false;
    private boolean dismissed = false;

    public HeraldNewsItemFormat(String title, String author, String originalDate, String
            updateDate, String originalTime, String updateTime, String postID, String
                                        imageURL,
                                String imageSavedLoc, String link, String desc, boolean fav,
                                boolean read, boolean dismissed) {

        this.title = title;
        this.author = author;
        this.originalDate = originalDate;
        this.updateDate = updateDate;
        this.originalTime = originalTime;
        this.updateTime = updateTime;
        this.postID = postID;
        this.imageURL = imageURL;
        this.imageSavedLoc = imageSavedLoc;
        this.link = link;
        this.desc = desc;
        this.fav = fav;
        this.read = read;
        this.dismissed = dismissed;
    }

    public HeraldNewsItemFormat() {
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(String originalDate) {
        this.originalDate = originalDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getOriginalTime() {
        return originalTime;
    }

    public void setOriginalTime(String originalTime) {
        this.originalTime = originalTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageSavedLoc() {
        return imageSavedLoc;
    }

    public void setImageSavedLoc(String imageSavedLoc) {
        this.imageSavedLoc = imageSavedLoc;
    }
}
