package com.csatimes.dojma;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vikramaditya Kukreja on 05-07-2016.
 */

public class HeraldLinks extends RealmObject {
    @PrimaryKey
    String address;

    public HeraldLinks() {
    }

    public HeraldLinks(String address) {
        this.address = address;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
