package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

/**
 * Model for `tag` field of DoJMA api.
 *
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
public class Tag {
    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("post_count")
    public String postCount;
}
