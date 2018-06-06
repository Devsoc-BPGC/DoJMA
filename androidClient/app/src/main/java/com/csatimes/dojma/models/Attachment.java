package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Model for `attachment` fields in Dojma api.
 *
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
public class Attachment extends RealmObject {
    @PrimaryKey
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

    /**
     * Saving full image as fullImage.
     */
    @Ignore
    @SerializedName("images")
    public HashMap<String, Image> images;

    public Image fullImage;
}
