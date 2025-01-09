package com.example.aiapplication;
import java.io.FileInputStream;
import java.io.IOException;
import android.content.Context;
import android.util.Log;

public class ReadData {
    //Method to read data from a file
    public static String returnData(Context context, String nameOfFile){
        //Built as string value
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fileInput = context.openFileInput(nameOfFile)) {
            int character;
            //UNtil not at end of file
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
