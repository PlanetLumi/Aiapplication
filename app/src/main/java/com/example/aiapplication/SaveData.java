package com.example.aiapplication;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class SaveData{
    public static void FileSave(Context context, String userData, String fileName) throws IOException {
        FileOutputStream fileOutput = context.openFileOutput(fileName, MODE_PRIVATE);
        OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput);
        outputWriter.write(userData);
        outputWriter.close();
        }
    public static void saveUserDb(Context context, String[] userData) throws IOException {
        buildDB db = new buildDB(context);
        saveUserID.saveID(context,db.populateCredentialDB(context, userData));
    }
    public void updateUserDb(Context context, String[] userData) throws IOException {
        buildDB db = new buildDB(context);
        db.updateDB(userData, saveUserID.grabID(context));
    }
}
