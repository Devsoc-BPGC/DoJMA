package com.csatimes.dojma;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class HeraldNewsItemFormat extends RealmObject {

    @PrimaryKey
    private String postID;
    private String title = "";
    private String title_plain = "";
    private String originalDate = "";
    private String originalMonthYear="";
    private String updateDate = "";
    private String originalTime = "";
    private String updateTime = "";
    private String imageURL = "";
    private String imageSavedLoc = "";
    private String url = "";
    private String content = "";
    private String excerpt = "";
    private String authorID = "";
    private String authorSlug = "";
    private String authorName = "";
    private String authorFName = "";
    private String authorLName = "";
    private String authorNName = "";
    private String authorURL = "";
    private String authorDesc = "";
    private String comment_status = "";
    private String categoryID = "";
    private String categorySlug = "";
    private String categoryTitle = "";
    private String categoryDescription;
    private int categoryCount = 0;
    private int comment_count = 0;
    private String type = "";
    private String slug = "";
    private String status = "";
    private boolean fav = false;
    private boolean read = false;
    private boolean dismissed = false;
    private String bigImageUrl = "";

    public HeraldNewsItemFormat() {

    }


    public HeraldNewsItemFormat(String postID, String title, String title_plain, String originalDate,String originalMonthYear,
                                String updateDate, String originalTime, String updateTime,
                                String imageURL, String imageSavedLoc, String url, String content,
                                String excerpt, String authorID, String authorSlug, String authorName,
                                String authorFName, String authorLName, String authorNName,
                                String authorURL, String authorDesc, String comment_status,
                                String categoryID, String categorySlug, String categoryTitle,
                                String categoryDescription, int categoryCount, int comment_count,
                                String type, String slug, String status, boolean fav, boolean read,
                                boolean dismissed, String bigImageUrl) {
        this.postID = postID;
        this.title = title;
        this.title_plain = title_plain;
        this.originalDate = originalDate;
        this.originalMonthYear=originalMonthYear;
        this.updateDate = updateDate;
        this.originalTime = originalTime;
        this.updateTime = updateTime;
        this.imageURL = imageURL;
        this.imageSavedLoc = imageSavedLoc;
        this.url = url;
        this.content = content;
        this.excerpt = excerpt;
        this.authorID = authorID;
        this.authorSlug = authorSlug;
        this.authorName = authorName;
        this.authorFName = authorFName;
        this.authorLName = authorLName;
        this.authorNName = authorNName;
        this.authorURL = authorURL;
        this.authorDesc = authorDesc;
        this.comment_status = comment_status;
        this.categoryID = categoryID;
        this.categorySlug = categorySlug;
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
        this.categoryCount = categoryCount;
        this.comment_count = comment_count;
        this.type = type;
        this.slug = slug;
        this.status = status;
        this.fav = fav;
        this.read = read;
        this.dismissed = dismissed;
        this.bigImageUrl = bigImageUrl;

    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_plain() {
        return title_plain;
    }

    public void setTitle_plain(String title_plain) {
        this.title_plain = title_plain;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(String originalDate) {
        this.originalDate = originalDate;
    }


    public String getOriginalMonthYear() {
        return originalMonthYear;
    }

    public void setOriginalMonthYear(String originalMonthYear) {
        this.originalMonthYear = originalMonthYear;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getAuthorSlug() {
        return authorSlug;
    }

    public void setAuthorSlug(String authorSlug) {
        this.authorSlug = authorSlug;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorFName() {
        return authorFName;
    }

    public void setAuthorFName(String authorFName) {
        this.authorFName = authorFName;
    }

    public String getAuthorLName() {
        return authorLName;
    }

    public void setAuthorLName(String authorLName) {
        this.authorLName = authorLName;
    }

    public String getAuthorNName() {
        return authorNName;
    }

    public void setAuthorNName(String authorNName) {
        this.authorNName = authorNName;
    }

    public String getAuthorURL() {
        return authorURL;
    }

    public void setAuthorURL(String authorURL) {
        this.authorURL = authorURL;
    }

    public String getAuthorDesc() {
        return authorDesc;
    }

    public void setAuthorDesc(String authorDesc) {
        this.authorDesc = authorDesc;
    }

    public String getComment_status() {
        return comment_status;
    }

    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }
}
