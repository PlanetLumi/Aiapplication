package com.example.aiapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class buildDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 13; //
    public static final String DATABASE_NAME = "UserDetails.db";
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
                    "passwordHash TEXT," +
                    "locked BOOLEAN DEFAULT 0," +
                    "loginAttempts INTEGER DEFAULT 0)";



    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS DATABASE_NAME";
    buildDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static synchronized buildDB getInstance(Context context) {
        if (instance == null) {
            instance = new buildDB(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_CREDENTIALS);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserCredentials");
        db.execSQL("DROP TABLE IF EXISTS UserDetails");
        onCreate(db);
    }

    public static void populateDB(Context context, long UserID) {
        SQLiteDatabase db = (buildDB.getInstance(context).getWritableDatabase());
        ContentValues values = new ContentValues();

        // Populate the ContentValues with column data
        values.put(defineDB.FeedEntry._ID, UserID);
        values.put(defineDB.FeedEntry.COLUMN_NAME_TITLE, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_FNAME, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_SNAME, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_PHONE, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_ADDRESS, "");

        // Insert the populated ContentValues into the database
        long newRowId = db.insert("UserDetails", null, values);
    }

    public static long populateCredentialDB(Context context, String[] userData) {
        SQLiteDatabase db = (buildDB.getInstance(context).getWritableDatabase());
        ContentValues values = new ContentValues();
        values.put("username", (userData[0].replace(",", "").toLowerCase()));
        values.put("passwordHash", hashingAlg.saltHash(userData[1], hashingAlg.saltGen()));
        long newRowId = db.insert("UserCredentials", null, values);
        populateDB(context, newRowId);
        return newRowId;
    }
    public static long readID(SQLiteDatabase db, String userName){
        String[] projection = {"_ID"};
        String selection = "username = ?";
        String[] selectionArgs = {userName};
        Cursor cursor = db.query(
                "UserCredentials",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor.getLong(cursor.getColumnIndexOrThrow("_ID"));

}
    public static String readDB(SQLiteDatabase db, String DATABASE_NAME, String[] list, long UserID) {
        String[] projection = list;
        String selection = defineDB.FeedEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(UserID)};

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
        if (cursor != null && cursor.moveToFirst()) {
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
    public void updateDB(Context context, String[] newData){
        String pastTitle = returnPastTitle(saveUserID.grabID(context));
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
                "UserDetails",
                values,
                selection,
                null);
    }
    public static long loginUser(SQLiteDatabase db, String username, String password) {
        username = (username.replace(",", "")).toLowerCase();
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
        if (userExists) {
            return readID(db, username);
        } else{
            return 1;
        }
    }

    public String returnPastTitle(long UserId){
        return buildDB.readDB(this.getReadableDatabase(), "UserDetails", new String[]{"Title","FName","SName"}, UserId);
    }

    public static boolean checkIfUserExists(SQLiteDatabase db, String username){
        username = (username.replace(",", ""));
        String selection = "username = ?";
        String[] selectionArgs = {username};
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
        return userExists;
    }
    public static void lockUser(SQLiteDatabase db, String username) {
        username = (username.replace(",", ""));
        ContentValues values = new ContentValues();
        values.put("locked", 1);
        String selection = "username = ?";
        String[] selectionArgs = {username};
        int rowsUpdated = db.update(
                "UserCredentials",
                values,
                selection,
                selectionArgs);
        if (rowsUpdated == 0) {
            throw new IllegalStateException("No rows were updated");
        }
    }
    public static boolean checkUserLocked(SQLiteDatabase db, String username){
        username = (username.replace(",", ""));
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(
                "UserCredentials",
                new String[]{"locked"},
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        boolean isLocked = false;
        if (cursor != null && cursor.moveToFirst()) {
            isLocked = cursor.getInt(cursor.getColumnIndexOrThrow("locked")) == 1;
        }
        cursor.close();
        return isLocked;
    }

    public static int getLoginAttempts(SQLiteDatabase db, String username) {
        username = (username.replace(",", ""));
        String[] projection = {"loginAttempts"};
        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                "UserCredentials",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        int loginAttempts = 0;
        if (cursor != null && cursor.moveToFirst()) {
            loginAttempts = cursor.getInt(cursor.getColumnIndexOrThrow("loginAttempts"));
        }
        cursor.close();
        return loginAttempts;
    }
    public static void userUnlocked(SQLiteDatabase db, String username) {
        username = (username.replace(",", ""));
        ContentValues values = new ContentValues();
        values.put("locked", 0);
        values.put("loginAttempts", 0);
        String selection = "username = ?";
        String[] selectionArgs = {username};
        db.update(
                "UserCredentials",
                values,
                selection,
                selectionArgs);
    }
    public static void incrementLoginAttempts(SQLiteDatabase db, String username) {
        int amountAttempts = getLoginAttempts(db, username);
        username = (username.replace(",", ""));
        ContentValues values = new ContentValues();
        values.put("loginAttempts", amountAttempts + 1);
        String selection = "username = ?";
        String[] selectionArgs = {username};
        db.update(
                "UserCredentials",
                values,
                selection,
                selectionArgs);

    }

    public static void resetLoginAttempts(SQLiteDatabase db, String username) {
        username = (username.replace(",", ""));
        ContentValues values = new ContentValues();
        values.put("loginAttempts", 0);
        String selection = "username = ?";
        String[] selectionArgs = {username};
        db.update(
                "UserCredentials",
                values,
                selection,
                selectionArgs);
    }

}
