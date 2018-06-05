package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

/**
 * Model for `category` field of DoJMA api.
 *
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
public class Category {
    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("post_count")
    public String postCount;
}
