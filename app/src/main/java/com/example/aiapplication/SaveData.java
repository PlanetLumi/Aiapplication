package com.example.aiapplication;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SaveData{
    public void FileSave(Context context, String userData,String fileName) throws IOException {
        FileOutputStream fileOutput = context.openFileOutput(fileName, MODE_PRIVATE);
        OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput);
        outputWriter.write(userData);
        outputWriter.close();
        }
    public void saveUserDb(Context context, String userData) throws IOException {
        String[] userDataArray = userData.split(",");
        String title = userDataArray[0] + userDataArray[1];
        buildDB db = new buildDB(context, "userData", null, 1);
        db.populateDB(context, userDataArray);
    }
    public void updateUserDb(Context context, String userData) throws IOException {
        String[] userDataArray = userData.split(",");
        String title = userDataArray[0] + userDataArray[1];
        buildDB db = new buildDB(context, "userData", null, 1);
        db.updateDB(title, userDataArray);
    }
}
