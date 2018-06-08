package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
public class SocialLink extends RealmObject {
    @PrimaryKey
    @SerializedName("name")
    public String name;

    @SerializedName("url")
    public String url;

    @SerializedName("thumbImageUrl")
    public String thumbImageUrl;
}
