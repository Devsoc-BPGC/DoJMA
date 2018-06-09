package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Rushikesh Jogdand.
 */
public class AndroidApp extends RealmObject {
    @SerializedName("id")
    public String id;

    @PrimaryKey
    @SerializedName("name")
    public String name;
}
