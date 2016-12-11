package com.csatimes.dojma.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * HeraldLinkItem contains only address field
 */

public class HeraldLinkItem extends RealmObject {
    @PrimaryKey
    private String address;

    public HeraldLinkItem() {
    }

    public HeraldLinkItem(String address) {
        this.address = address;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
