package com.csatimes.dojma.models;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by vikramaditya on 6/1/17.
 */

public class MessItem extends RealmObject {
    @Ignore
    public static final String FIELD_TITLE = "title";
    public String imageUrl;
    public String title;
}
