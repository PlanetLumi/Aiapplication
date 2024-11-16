package com.example.aiapplication;
import java.io.FileInputStream;
import java.io.IOException;
import android.content.Context;

public class ReadData {
    public String returnData(Context context, String nameOfFile){
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fileInput = context.openFileInput(nameOfFile)) {
            int character;
            while ((character = fileInput.read()) != -1) {
                stringBuilder.append((char) character);
            }
            stringBuilder.append("1");
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
