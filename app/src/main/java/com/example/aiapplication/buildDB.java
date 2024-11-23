package com.example.aiapplication;

import static okhttp3.internal.Internal.instance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class buildDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "user_Details.db";
    private static buildDB instance;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + defineDB.FeedEntry.TABLE_NAME + " (" +
            defineDB.FeedEntry._ID + " INTEGER PRIMARY KEY," +
            defineDB.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
            defineDB.FeedEntry.COLUMN_NAME_ID + " TEXT," +
            defineDB.FeedEntry.COLUMN_NAME_FNAME + " TEXT," +
            defineDB.FeedEntry.COLUMN_NAME_SNAME + " TEXT," +
            defineDB.FeedEntry.COLUMN_NAME_PHONE + " TEXT," +
            defineDB.FeedEntry.COLUMN_NAME_ADDRESS + " TEXT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + defineDB.FeedEntry.TABLE_NAME;

    public buildDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    static synchronized buildDB getInstance(Context context, String databasename) {
        if (instance == null) {
            instance = new buildDB(context.getApplicationContext(), databasename, null, 1);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void populateDB(Context context, String[] userData){
        String title = userData[0] + userData[1];
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.insert(defineDB.FeedEntry.TABLE_NAME, null, values);
        values.put(defineDB.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(defineDB.FeedEntry.COLUMN_NAME_FNAME, userData[0]);
        values.put(defineDB.FeedEntry.COLUMN_NAME_SNAME, userData[1]);
        values.put(defineDB.FeedEntry.COLUMN_NAME_PHONE, userData[2]);
        values.put(defineDB.FeedEntry.COLUMN_NAME_ADDRESS, userData[3]);
    }
    public static String readDB(SQLiteDatabase db,String title) {
        String[] projection = {
                defineDB.FeedEntry._ID,
                defineDB.FeedEntry.COLUMN_NAME_TITLE,
                defineDB.FeedEntry.COLUMN_NAME_FNAME,
                defineDB.FeedEntry.COLUMN_NAME_SNAME,
                defineDB.FeedEntry.COLUMN_NAME_PHONE,
                defineDB.FeedEntry.COLUMN_NAME_ADDRESS
        };
        String selection = defineDB.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {title};
        String sortOrder = defineDB.FeedEntry._ID + " DESC";

        Cursor cursor = db.query(
                defineDB.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        return cursor.toString();
    }
    public void updateDB(String title, String[] newData){
        String newTitle = newData[0] + newData[1];
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(defineDB.FeedEntry.COLUMN_NAME_TITLE, newTitle);
        values.put(defineDB.FeedEntry.COLUMN_NAME_FNAME, newData[1]);
        values.put(defineDB.FeedEntry.COLUMN_NAME_SNAME, newData[2]);
        values.put(defineDB.FeedEntry.COLUMN_NAME_PHONE, newData[3]);
        values.put(defineDB.FeedEntry.COLUMN_NAME_ADDRESS, newData[4]);
        String selection = defineDB.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {title};
        db.update(
                defineDB.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    public static boolean checkUser(SQLiteDatabase db, String title){
        String selection = defineDB.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {title};
        Cursor cursor = db.query(
                defineDB.FeedEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor.getCount() > 0;
    }
}
