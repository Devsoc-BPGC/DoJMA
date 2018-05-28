package com.csatimes.dojma.models;

public class Contributor {
    public String name;
    public String phone;
    public String email;
    public String web;
    public String photoUrl;

    public Contributor() {
    }

    public Contributor(String name, String phone, String email, String web, String photoUrl) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.web = web;
        this.photoUrl = photoUrl;
    }
}
