package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * Model for `post` field of DoJMA api.
 *
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
public class Post {
    @SerializedName("id")
    public int id;

    @SerializedName("url")
    public String url;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @SerializedName("excerpt")
    public String excerpt;

    @SerializedName("categories")
    public List<Category> categories;

    @SerializedName("tags")
    public List<Tag> tags;

    @SerializedName("author")
    public Author author;

    @SerializedName("attachments")
    public List<Attachment> attachments;

    @SerializedName("comment_count")
    public int commentCount;

    @SerializedName("comment_status")
    public String commentStatus;

    @SerializedName("thumbnail")
    public String thumbnail;

    @SerializedName("thumbnail_images")
    public HashMap<String, Image> thumbnailImages;

    @SerializedName("date")
    public String date;

    @SerializedName("modified")
    public String modified;
}
