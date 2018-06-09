package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

import androidx.annotation.Keep;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
@Keep
public class Person extends RealmObject {
    @Ignore
    public static final String FIELD_NAME = "name";

    @PrimaryKey
    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

    @SerializedName("phone")
    public String phone;

    @Nullable
    @SerializedName("postName")
    public String postName;

    @Ignore
    public static final String FIELD_POST_NAME = "postName";

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("homePage")
    public String homePage;

    public boolean isContributor = false;
}
