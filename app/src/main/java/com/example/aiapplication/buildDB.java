package com.example.aiapplication;

import static okhttp3.internal.Internal.instance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class buildDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 7;
    private static buildDB instance;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE UserDetails (" +
                    defineDB.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    defineDB.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_FNAME + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_SNAME + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_PHONE + " TEXT," +
                    defineDB.FeedEntry.COLUMN_NAME_ADDRESS + " TEXT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + defineDB.FeedEntry.TABLE_NAME;

    public buildDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    static synchronized buildDB getInstance(Context context) {
        if (instance == null) {
            instance = new buildDB(context.getApplicationContext(), null, null, DATABASE_VERSION);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserDetails");
        onCreate(db);
    }

    public void populateDB(Context context, String[] userData) {
        String title = userData[0] + userData[1]; // Combine first and last name to generate the title
        SQLiteDatabase db = (buildDB.getInstance(context).getWritableDatabase());
        ContentValues values = new ContentValues();

        // Populate the ContentValues with column data
        values.put(defineDB.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(defineDB.FeedEntry.COLUMN_NAME_FNAME, userData[0]);
        values.put(defineDB.FeedEntry.COLUMN_NAME_SNAME, userData[1]);
        values.put(defineDB.FeedEntry.COLUMN_NAME_PHONE, userData[2]);
        values.put(defineDB.FeedEntry.COLUMN_NAME_ADDRESS, userData[3]);
        System.out.println("Inserting title: " + title);


        // Insert the populated ContentValues into the database
        long newRowId = db.insert("UserDetails", null, values);
        if (newRowId == -1) {
            System.err.println("Error inserting data into UserDetails");
        } else {
            System.out.println("Data inserted with title: " + title);
        }
        db.close();
    }

    public static String readDB(SQLiteDatabase db) {
        String[] projection = {
                defineDB.FeedEntry._ID,
                defineDB.FeedEntry.COLUMN_NAME_TITLE,
                defineDB.FeedEntry.COLUMN_NAME_FNAME,
                defineDB.FeedEntry.COLUMN_NAME_SNAME,
                defineDB.FeedEntry.COLUMN_NAME_PHONE,
                defineDB.FeedEntry.COLUMN_NAME_ADDRESS
        };

        Cursor cursor = db.query(
                "UserDetails",
                projection,
                null,
                null,
                null,
                null,
                defineDB.FeedEntry._ID + " DESC"
        );
        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                String titleValue = cursor.getString(cursor.getColumnIndexOrThrow(defineDB.FeedEntry.COLUMN_NAME_TITLE));
                String fName = cursor.getString(cursor.getColumnIndexOrThrow(defineDB.FeedEntry.COLUMN_NAME_FNAME));
                String sName = cursor.getString(cursor.getColumnIndexOrThrow(defineDB.FeedEntry.COLUMN_NAME_SNAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(defineDB.FeedEntry.COLUMN_NAME_PHONE));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(defineDB.FeedEntry.COLUMN_NAME_ADDRESS));
                result.append(titleValue).append(",")
                        .append(fName).append(",")
                        .append(sName).append(",")
                        .append(phone).append(",")
                        .append(address).append(",");
            } while (cursor.moveToNext());
        } else {
            result.append("No data found");
        }
        cursor.close();
        return result.toString();
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
                "UserDetails",
                values,
                selection,
                selectionArgs);
    }
    public static boolean checkUser(SQLiteDatabase db, String userData){
        String[] userDataArray = userData.split(",");
        String title = userDataArray[0] + userDataArray[1];
        String selection = defineDB.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {title};
        Cursor cursor = db.query(
                "UserDetails",
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
