package com.csatimes.dojma.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * ContactItem has name ,number, email, type
 */

public class ContactItem extends RealmObject {

    @Index
    private String type;
    private String name;
    private String number;
    private String email;
    private String sub1;
    private String sub2;
    private String icon;
    private int id;

    public ContactItem() {
        this.name = "";
        this.number = "";
        this.email = "";
        this.type = "Others";
        this.id = 0;
        this.sub1 = "";
        this.sub2 = "";
        this.icon = "";
    }

    public ContactItem(String name, String number, String email, String sub1, String sub2, String type, String icon, int id) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.type = type;
        this.id = id;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
