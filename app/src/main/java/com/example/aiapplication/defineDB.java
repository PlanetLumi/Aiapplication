package com.example.aiapplication;

import android.provider.BaseColumns;

public class defineDB {
    private defineDB() {}
    public static class FeedEntry implements BaseColumns {
        public static final String _ID = "ID";
        public static final String COLUMN_NAME_FNAME = "FName";
        public static final String COLUMN_NAME_SNAME = "SName";
        public static final String COLUMN_NAME_PHONE = "PNumber";
        public static final String COLUMN_NAME_ADDRESS = "Address";
    }
}
