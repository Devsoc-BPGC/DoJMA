package com.csatimes.dojma.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * ContactItem has name ,number, email, type
 */

public class ContactItem extends RealmObject {
    private String name;
    private String number;
    private String email;
    @Index
    private String type;
    private int id;

    public ContactItem() {
        this.name = "No name";
        this.number = "0";
        this.email = "";
        this.type = "";
        this.id = 0;
    }

    public ContactItem(String name, String number, String email, String type, int id) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.type = type;
        this.id = id;
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
}
