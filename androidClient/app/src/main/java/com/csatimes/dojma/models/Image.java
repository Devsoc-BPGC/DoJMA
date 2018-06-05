package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

/**
 * Model for `author` field of DoJMA api.
 *
 * @author Rushikesh Jogdand.
 */
public class Image {
    @SerializedName("url")
    public String url;

    @SerializedName("width")
    public int width;

    @SerializedName("height")
    public int height;
}
