package com.example.aiapplication;

import static okhttp3.internal.Internal.instance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Arrays;

import okhttp3.EventListener;

public class buildDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 8; //
    private static buildDB instance;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE UserDetails (" +
                    defineDB.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    defineDB.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_FNAME + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_SNAME + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_PHONE + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_ADDRESS + " TEXT)";

    private static final String SQL_CREATE_USER_CREDENTIALS  =
            "CREATE TABLE UserCredentials (" +
                    "userID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE," +
                    "passwordHash TEXT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS DATABASE_NAME";
    buildDB(@Nullable Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static synchronized buildDB getInstance(Context context, String DATABASE_NAME) {
        if (instance == null) {
            instance = new buildDB(context.getApplicationContext(), DATABASE_NAME);
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

    public void populateDB(Context context, String DATABASE_NAME) {
        SQLiteDatabase db = (buildDB.getInstance(context, DATABASE_NAME).getWritableDatabase());
        ContentValues values = new ContentValues();

        // Populate the ContentValues with column data
        values.put(defineDB.FeedEntry.COLUMN_NAME_TITLE, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_FNAME, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_SNAME, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_PHONE, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_ADDRESS, "");

        // Insert the populated ContentValues into the database
        long newRowId = db.insert(DATABASE_NAME, null, values);
        db.close();
    }

    public void populateCredentialDB(Context context, String[] userData, String DATABASE_NAME) {
        SQLiteDatabase db = (buildDB.getInstance(context, DATABASE_NAME).getWritableDatabase());
        ContentValues values = new ContentValues();
        values.put("username", userData[0].replace(",", ""));
        values.put("passwordHash", hashingAlg.saltHash(userData[1], hashingAlg.saltGen()));
        long newRowId = db.insert(DATABASE_NAME, null, values);
        db.close();
        populateDB(context,"UserDetails.db");
    }

    public static String readDB(SQLiteDatabase db, String DATABASE_NAME, String[] list, String UserID) {
        String[] projection = list;
        String selection = defineDB.FeedEntry._ID + " = ?";
        String[] selectionArgs = {UserID};

        Cursor cursor = db.query(
                DATABASE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                defineDB.FeedEntry._ID + " DESC"
        );
        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
               for(String s : list){
                   String value = cursor.getString(cursor.getColumnIndexOrThrow(s));
                   result.append(value).append(",");
               }
                result.append("\\n");
            } while (cursor.moveToNext());
        } else {
            result.append("No data found");
        }
        cursor.close();
        return result.toString();
    }
    public void updateDB(String[] newData, String UserID){
        String pastTitle = returnPastTitle(UserID);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] pastTitleEl = (pastTitle.split(","));
        String title = pastTitleEl[0];
        if (!newData[0].isEmpty()){
            title = title.replace(pastTitleEl[1], newData[0]);
            values.put(defineDB.FeedEntry.COLUMN_NAME_FNAME, newData[0]);
        }
        if (!newData[1].isEmpty()){
            title = title.replace(pastTitleEl[2], newData[1]);
            values.put(defineDB.FeedEntry.COLUMN_NAME_SNAME, newData[1]);
        }
        values.put(defineDB.FeedEntry.COLUMN_NAME_TITLE, title);
        if(!newData[2].isEmpty()){
            values.put(defineDB.FeedEntry.COLUMN_NAME_PHONE, newData[2]);
        }
        if(!newData[3].isEmpty()){
            values.put(defineDB.FeedEntry.COLUMN_NAME_ADDRESS, newData[3]);
        }
        String selection = defineDB.FeedEntry._ID + " = ?";
        db.update(
                "UserDetails.db",
                values,
                selection,
                null);
    }
    public static boolean loginUser(SQLiteDatabase db, String username, String password) {
        username = username.replace(",", "");
        password = password.replace(",", "");


        String selection = "username = ? AND passwordHash = ?";
        String[] selectionArgs = {username, hashingAlg.saltHash(password, hashingAlg.saltGen())};

        Cursor cursor = db.query(
                "UserCredentials",
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return userExists;
    }
    public String returnPastTitle(String UserId){
        return buildDB.readDB(this.getReadableDatabase(), "UserDetails", new String[]{"Title","FName","SName"}, UserId);
    }
}
