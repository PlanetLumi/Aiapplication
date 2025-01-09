package com.example.aiapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class SaveData {
    //Saves the option they selected
    public static void saveOption(Context context, String userData, String fileName) throws IOException {
        fileClear(context, fileName);
        FileSave(context, userData, fileName);
    }

    //Clears the file
    public static void fileClear(Context context, String fileName) throws IOException {
        FileOutputStream fileOutput = context.openFileOutput(fileName, MODE_PRIVATE);
        OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput);
        outputWriter.write("");
        outputWriter.close();
    }
    //Saves inputted data to the target file
    public static void FileSave(Context context, String userData, String fileName) throws IOException {
        FileOutputStream fileOutput = context.openFileOutput(fileName, MODE_PRIVATE);
        OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput);
        outputWriter.write(userData);
        outputWriter.close();
        }
    //SAves the ID to intent of application
    public static void saveUserDb(Context context, String[] userData) throws IOException {
        saveUserID.saveID(context, buildDB.populateCredentialDB(context, userData));
    }
    //Updates user data
    public void updateUserDb(Context context, String[] userData) throws IOException {
        buildDB db = new buildDB(context);
        db.updateDB(userData, saveUserID.grabID(context));
    }
}
