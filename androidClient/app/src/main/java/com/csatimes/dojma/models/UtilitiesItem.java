package com.csatimes.dojma.models;



public class UtilitiesItem {
    private String utility, type;
    private int icon;

    public UtilitiesItem (String utility, int icon, String type){
        this.utility=utility;
        this.icon = icon;
        this.type = type;
    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
