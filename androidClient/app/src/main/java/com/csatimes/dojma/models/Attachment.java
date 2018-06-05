package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Model for `attachment` fields in Dojma api.
 *
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
public class Attachment {
    @SerializedName("id")
    public int id;

    @SerializedName("url")
    public String url;

    @SerializedName("title")
    public String title;

    @SerializedName("slug")
    public String slug;

    @SerializedName("description")
    public String description;

    @SerializedName("caption")
    public String caption;

    @SerializedName("parent")
    public int parent;

    @SerializedName("mime_type")
    public String mimeType;

    @SerializedName("images")
    public HashMap<String, Image> images;
}
