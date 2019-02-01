package com.csatimes.dojma.models;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;

/**
 * ContactItem has name ,number, email, type
 */

public class ContactItem extends RealmObject {
    @Ignore
    public static final String FIELD_NAME = "name";

    @Ignore
    public static final String FIELD_NUMBER = "number";

    @Ignore
    public static final String FIELD_EMAIL = "email";


    @Ignore
    public static final String FIELD_TYPE = "type";

    @Ignore
    public static final String FIELD_SUB1 = "sub1";

    @Ignore
    public static final String FIELD_SUB2 = "sub2";

    @Index
    public String type;
    public String name;
    public String number;
    public String email;
    public String sub1;
    public String sub2;
    public String icon;
    public int id;
}
