package com.example.aiapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class initialiseSettings {

        // Method to initialize the settings.txt file with default values
        public static void initialise(Context context) throws FileNotFoundException {
            String defaultSettings = "[stylePalette, Default, permissions, true, notifications, true]";
            try {
                FileOutputStream fileOutput = context.openFileOutput("settings.txt", Context.MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput);
                outputWriter.write(defaultSettings);
                outputWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileNotFoundException("Unable to create settings file");
            }
        }
    }