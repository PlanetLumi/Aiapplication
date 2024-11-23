package com.example.aiapplication;
import java.io.FileInputStream;
import java.io.IOException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ReadData {
    public static String returnData(Context context, String nameOfFile){
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fileInput = context.openFileInput(nameOfFile)) {
            int character;
            while ((character = fileInput.read()) != -1) {
                stringBuilder.append((char) character);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String returnDBUserData(Context context){
        return buildDB.readDB(buildDB.getInstance(context).getReadableDatabase());
    }
}
