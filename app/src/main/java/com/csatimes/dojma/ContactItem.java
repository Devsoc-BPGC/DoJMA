package com.csatimes.dojma;

/**
 * Created by Vikramaditya Kukreja on 22-07-2016.
 */

public class ContactItem {
    String name;
    String number;

    public ContactItem() {
        this.name = "No name";
        this.number = "0";
    }

    public ContactItem(String name, String number) {
        this.name = name;
        this.number = number;
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
}
