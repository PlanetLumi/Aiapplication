package com.example.aiapplication;
import java.io.FileInputStream;
import java.io.IOException;
import android.content.Context;
import android.util.Log;

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
            Log.d("FIle Read Error", "Error reading file");
            throw new RuntimeException(e);
        }
    }

}
