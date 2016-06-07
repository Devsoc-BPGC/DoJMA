package com.csatimes.dojma;

import android.provider.BaseColumns;

/**
 * Created by Vikramaditya Kukreja on 23-05-2016.
 */
public class TableData {
public TableData()
{

}
    public static abstract class TableInfo implements BaseColumns{
        public static final String tablePostID = "_id";
        public static final String tableTitle="ARTICLE_TITLE";
        public static final String tableImageURL="IMAGE_URL";
        public static final String tableImageSavedLoc="IMAGE_LOCATION";
        public static final String tableDate="DATE_OF_ORIGINAL_POST";
        public static final String tableUpdateDate = "DATE_OF_POST_UPDATE";
        public static final String tableAuthor="AUTHOR";
        public static final String tableURL = "POST_URL";
        //DATABASE and TABLE NAME
        public static final String DATABASE_NAME = "HERALD_DATABASE";
        public static final String TABLE_NAME="NEWS_ITEM_DATA";
    }
}
