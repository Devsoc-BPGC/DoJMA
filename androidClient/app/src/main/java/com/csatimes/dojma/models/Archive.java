package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Archive extends RealmObject {
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("title")
    public String title;
}
