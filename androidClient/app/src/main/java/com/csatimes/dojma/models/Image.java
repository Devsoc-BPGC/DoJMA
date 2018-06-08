package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model for `author` field of DoJMA api.
 *
 * @author Rushikesh Jogdand.
 */
public class Image extends RealmObject {
    @PrimaryKey
    @SerializedName("url")
    public String url;

    @SerializedName("width")
    public int width;

    @SerializedName("height")
    public int height;
}
