
package com.example.aiapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataGrab extends AppCompatActivity {

    public static Collection<Integer> fillDataGrabs(Context context, String[] idNames){
        List<Integer> dataPoints = new ArrayList<>();
        for(int i = 0; i < idNames.length; i++) {
            int resourceId = context.getResources().getIdentifier(idNames[i], "id", context.getPackageName());
            dataPoints.add(resourceId);
        }
        return dataPoints;
    }

    public static String gatherData(Context context, String[] idNames){
        List<Integer> dataPoints = (List<Integer>) fillDataGrabs(context, idNames);
        List<Object> userData = new ArrayList<>();
        for(int i = 0; i < dataPoints.size(); i++) {
            EditText current = (EditText) ((AppCompatActivity) context).findViewById(dataPoints.get(i));
            userData.add(current.getText().toString());
        }
        return userData.toString();
    }
}
