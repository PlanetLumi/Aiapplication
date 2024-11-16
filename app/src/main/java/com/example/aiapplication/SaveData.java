package com.example.aiapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SaveData{
    public void FileSave(Context context, String userData) throws IOException {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "UserData.txt");
        context.startActivity(intent);
        FileOutputStream fileOutput = context.openFileOutput("UserData.txt", MODE_PRIVATE);
        OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput);
        outputWriter.write(userData);
        outputWriter.close();
    }
}
