package com.csatimes.dojma.models;

/**
 * Created by vikramaditya on 17/1/17.
 */

public class TypeItem {
    private int type;
    private Object value;

    public TypeItem(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
