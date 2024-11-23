package com.example.aiapplication;

import android.provider.BaseColumns;

public class defineDB {
    private defineDB() {}
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "UserDetails";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_FNAME = "FirstName";
        public static final String COLUMN_NAME_SNAME = "LastName";
        public static final String COLUMN_NAME_PHONE = "PhoneNumber";
        public static final String COLUMN_NAME_ADDRESS = "Address";
    }
}
