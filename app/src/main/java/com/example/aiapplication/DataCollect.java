package com.example.aiapplication;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DataCollect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        int layoutId = setPalette.setLayout(DataCollect.this, "data_collect");
        setContentView(layoutId);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.datacollect), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Adds already saved details back into activity.
        setBoxFunc.setBoxes(DataCollect.this, new String[]{"FName", "SName", "PNumber", "Address"});
        saveButtonFunc.saveDbBtn(DataCollect.this, DataCollect.this, new String[]{"FName", "SName", "PNumber", "Address"}, null);
        ExitButtonFunc.exitBtn(DataCollect.this, MainMenu.class);
    }
}