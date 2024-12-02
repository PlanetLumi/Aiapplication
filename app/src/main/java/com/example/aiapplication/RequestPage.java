package com.example.aiapplication;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RequestPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        int layoutId = setPalette.setLayout(RequestPage.this, "request_page");
        setContentView(layoutId);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.requestPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        saveButtonFunc.funcSaveBtn(RequestPage.this,RequestPage.this, new String[]{"companyInp", "detailsInp","usePersonalData"}, "userRequestInput.txt", GenerationArea.class);
        ExitButtonFunc.exitBtn(RequestPage.this, MainMenu.class);
    }
}