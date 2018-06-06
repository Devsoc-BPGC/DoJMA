package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model for `author` field of DoJMA api.
 *
 * @author Rushikesh Jogdand.
 */

@SuppressWarnings("DuplicateStringLiteralInspection")
public class Author extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    public int id;

    @SerializedName("slug")
    public String slug;

    @SerializedName("name")
    public String name;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("nick_name")
    public String nickName;

    @SerializedName("url")
    public String url;

    @SerializedName("description")
    public String description;
}
