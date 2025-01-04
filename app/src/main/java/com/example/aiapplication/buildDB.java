package com.example.aiapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class buildDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 54; //
    private static buildDB instance;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE UserDetails (" +
                    defineDB.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    defineDB.FeedEntry.COLUMN_NAME_FNAME + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_SNAME + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_PHONE + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_ADDRESS + " TEXT)";

    private static final String SQL_CREATE_USER_CREDENTIALS =
            "CREATE TABLE UserCredentials (" +
                    "userID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE," +
                    "passwordHash TEXT," +
                    "salt TEXT," +
                    "locked BOOLEAN DEFAULT 0," +
                    "lockTime INTEGER DEFAULT 0," +
                    "loginAttempts INTEGER DEFAULT 0)";

    buildDB(@Nullable Context context) {
        super(context, "UserDetails.db", null, DATABASE_VERSION);
    }

    static synchronized buildDB getInstance(Context context)  {
        if (instance == null) {
            instance = new buildDB(context.getApplicationContext());
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
        instance = null;
    }

    public static void populateDB(Context context, long UserID) {
        ContentValues values = new ContentValues();

        // Populate the ContentValues with column data
        values.put(defineDB.FeedEntry._ID, UserID);
        values.put(defineDB.FeedEntry.COLUMN_NAME_FNAME, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_SNAME, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_PHONE, "");
        values.put(defineDB.FeedEntry.COLUMN_NAME_ADDRESS, "");

        // Insert the populated ContentValues into the database
        buildDB.getInstance(context).getWritableDatabase().insert("UserDetails", null, values);
    }

    public static long populateCredentialDB(Context context, String[] userData) {
        ContentValues values = new ContentValues();
        values.put("username", (userData[0].toLowerCase()));
        String salt = hashingAlg.saltGen();
        values.put("salt", salt);
        values.put("passwordHash", hashingAlg.saltHash(userData[1], salt));

        long newRowId = buildDB.getInstance(context).getWritableDatabase().insert("UserCredentials", null, values);
        populateDB(context, newRowId);
        return newRowId;
    }

    public static long readID(SQLiteDatabase db, String userName) {
        String[] projection = {"userID"};
        String selection = "username = ?";
        String[] selectionArgs = {userName.toLowerCase()};
        Cursor cursor = db.query(
                "UserCredentials",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        long userId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndexOrThrow("userID"));
            cursor.close();
        }
        return userId;

    }

    public static String readDB(SQLiteDatabase db, String[] list, long UserID) {
        String selection = defineDB.FeedEntry._ID + " = ?";
        //Sanitising SQL injections
        List<String> validColumns = Arrays.asList(
                defineDB.FeedEntry._ID,
                defineDB.FeedEntry.COLUMN_NAME_FNAME,
                defineDB.FeedEntry.COLUMN_NAME_SNAME,
                defineDB.FeedEntry.COLUMN_NAME_PHONE,
                defineDB.FeedEntry.COLUMN_NAME_ADDRESS
        );
        for (String column : list) {
            if (!validColumns.contains(column)) {
                throw new IllegalArgumentException("Invalid column name: " + column);
            }
        }

        Cursor cursor = db.query(
                "UserDetails",
                list,
                selection,
                new String[]{String.valueOf(UserID)},
                null,
                null,
                null
        );
        StringBuilder result = new StringBuilder();
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                for (String s : list) {
                    String value = cursor.getString(cursor.getColumnIndexOrThrow(s));
                    result.append(value);
                    if (count > 0) {
                        result.append(",");
                        result.append("\\n");
                    }
                    count++;
                }
            } while (cursor.moveToNext());
        } else {
            result.append("No data found");
        }
        assert cursor != null;
        cursor.close();
        return result.toString();
    }

    public void updateDB(String[] newData, long UserID) {
        ContentValues values = new ContentValues();
        if (!newData[0].isEmpty()) {
            values.put(defineDB.FeedEntry.COLUMN_NAME_FNAME, newData[0]);
        }
        if (!newData[1].isEmpty()) {
            values.put(defineDB.FeedEntry.COLUMN_NAME_SNAME, newData[1]);
        }
        if (!newData[2].isEmpty()) {
            values.put(defineDB.FeedEntry.COLUMN_NAME_PHONE, newData[2]);
        }
        if (!newData[3].isEmpty()) {
            values.put(defineDB.FeedEntry.COLUMN_NAME_ADDRESS, newData[3]);
        }
        String selection = defineDB.FeedEntry._ID + " = ?";
        this.getWritableDatabase().update(
                "UserDetails",
                values,
                selection,
                new String[]{String.valueOf(UserID)});
    }

    public static long loginUser(SQLiteDatabase db, String username, String password) {
        username = (username).toLowerCase();
        String[] projection = {"passwordHash", "salt"};
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
        if (cursor != null && cursor.moveToFirst()) {
            String storedPasswordHash = cursor.getString(cursor.getColumnIndexOrThrow("passwordHash"));
            String storedSalt = cursor.getString(cursor.getColumnIndexOrThrow("salt"));
            cursor.close();
            String inputPasswordHash = hashingAlg.saltHash(password, storedSalt);
            if (storedPasswordHash.equals(inputPasswordHash)) {
                return readID(db, username);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return -1;
    }

    public static boolean checkIfUserExists(SQLiteDatabase db, String username) {
        String selection = "username = ?";
        String[] selectionArgs = {username.toLowerCase()};
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
        ContentValues values = new ContentValues();
        values.put("locked", 1);
        values.put("lockTime", System.currentTimeMillis());
        String selection = "username = ?";
        String[] selectionArgs = {username.toLowerCase()};
        int rowsUpdated = db.update(
                "UserCredentials",
                values,
                selection,
                selectionArgs);
        if (rowsUpdated == 0) {
            throw new IllegalStateException("No rows were updated");
        }
    }

    public static boolean checkUserLockedTime(SQLiteDatabase db, String username) {
        username = (username).toLowerCase();
        String selection = "username = ?";
        String[] selectionArgs = {username};
        String[] projection = {"locked", "lockTime"};
        Cursor cursor = db.query(
                "UserCredentials",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        boolean isLocked = false;
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getInt(cursor.getColumnIndexOrThrow("locked")) == 1) {
                long lockDuration = findLockDuration(db, username);
                if(lockDuration > 100000){
                    isLocked = true;
                } else {
                    if (System.currentTimeMillis() - cursor.getLong(cursor.getColumnIndexOrThrow("lockTime")) >= lockDuration){
                        userUnlocked(db, username);
                    } else {
                        isLocked = true;
                    }
                }
            }
            cursor.close();
        }
        return isLocked;
    }
        public static int getLoginAttempts (SQLiteDatabase db, String username){
            String[] projection = {"loginAttempts"};
            String selection = "username = ?";
            String[] selectionArgs = {username.toLowerCase()};

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

    public static long findLockDuration(SQLiteDatabase db, String username) {
        return  getLoginAttempts(db, username) * 600000L;

    }
    public static long findCurrentDuration(SQLiteDatabase db, String username){
        username = (username).toLowerCase();
        String selection = "username = ?";
        String[] selectionArgs = {username};
        String[] projection = {"lockTime"};
        Cursor cursor = db.query(
                "UserCredentials",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        cursor.close();
        return (findLockDuration(db, username) - (System.currentTimeMillis() - cursor.getLong(cursor.getColumnIndexOrThrow("lockTime")))) / 60000;
    }
    public static void userUnlocked(SQLiteDatabase db, String username) {
        ContentValues values = new ContentValues();
        values.put("locked", 0);
        values.put("lockTime", 0);
        String selection = "username = ?";
        String[] selectionArgs = {username.toLowerCase()};
        db.update(
                "UserCredentials",
                values,
                selection,
                selectionArgs);
    }
    public static void incrementLoginAttempts(SQLiteDatabase db, String username) {
        username = (username).toLowerCase();
        ContentValues values = new ContentValues();
        values.put("loginAttempts", getLoginAttempts(db, username) + 1);
        String selection = "username = ?";
        String[] selectionArgs = {username};
        db.update(
                "UserCredentials",
                values,
                selection,
                selectionArgs);

    }

    public static void resetLoginAttempts(SQLiteDatabase db, String username) {
        ContentValues values = new ContentValues();
        values.put("loginAttempts", 0);
        values.put("lockTime", 0);
        String selection = "username = ?";
        String[] selectionArgs = {username.toLowerCase()};
        db.update(
                "UserCredentials",
                values,
                selection,
                selectionArgs);
    }
    public static boolean checkSpam(SQLiteDatabase db){
        int count = 0;
        try{
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM UserCredentials", null);
            if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
            }
            cursor.close();
            return count > 10;
            } catch (Exception e){
                e.printStackTrace();
        }
        return count > 10;


    }
}
