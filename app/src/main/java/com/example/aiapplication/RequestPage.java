package com.example.aiapplication;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.MasterKeys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import android.net.Uri;
import android.util.Log;

public class RequestPage extends AppCompatActivity {
    private static final String TAG = "AttachmentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int layoutId = setPalette.setLayout(RequestPage.this, "request_page");
        setContentView(layoutId);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.requestPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        saveButtonFunc.funcSaveBtn(RequestPage.this, RequestPage.this, new String[]{"companyInp", "detailsInp", "usePersonalData"}, "userRequestInput.txt", GenerationArea.class);
        ExitButtonFunc.exitBtn(RequestPage.this, MainMenu.class);
        ImageButton attachBtn = findViewById(R.id.attachButton);
        attachBtn.setOnClickListener(v -> {
            Attachments attach = new Attachments(RequestPage.this);
            Attachments.requestPermissions(RequestPage.this);
            attach.showOptionDialog(RequestPage.this);

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Attachments.REQUEST_SELECT_FILE && data != null) {
                Log.d(TAG, "File selection successful");
                List<String> encryptedFilePaths = new ArrayList<>();

                if (data.getClipData() != null) {
                    // Handle multiple files
                    int count = data.getClipData().getItemCount();
                    Log.d(TAG, "Number of files selected: " + count);

                    for (int i = 0; i < count; i++) {
                        Uri fileUri = data.getClipData().getItemAt(i).getUri();
                        Log.d(TAG, "Processing file URI: " + fileUri.toString());
                        String encryptedPath = encryptAndSaveFile(fileUri);
                        if (encryptedPath != null) {
                            Log.d(TAG, "File encrypted and saved at: " + encryptedPath);
                            encryptedFilePaths.add(encryptedPath);
                        } else {
                            Log.e(TAG, "Failed to encrypt and save file: " + fileUri.toString());
                        }
                    }
                } else if (data.getData() != null) {
                    // Handle single file
                    Uri fileUri = data.getData();
                    Log.d(TAG, "Processing single file URI: " + fileUri.toString());
                    String encryptedPath = encryptAndSaveFile(fileUri);
                    if (encryptedPath != null) {
                        Log.d(TAG, "File encrypted and saved at: " + encryptedPath);
                        encryptedFilePaths.add(encryptedPath);
                    } else {
                        Log.e(TAG, "Failed to encrypt and save file: " + fileUri.toString());
                    }
                }

                // Pass the list of encrypted file paths to GenerationArea
                Intent intent = new Intent(this, GenerationArea.class);
                intent.putStringArrayListExtra("encryptedFilePaths", new ArrayList<>(encryptedFilePaths));
                intent.putExtra("userRequestInputFilePath", new File(getFilesDir(), "userRequestInput.txt").getAbsolutePath());
                Log.d(TAG, "Starting GenerationArea activity with encrypted file paths");
                startActivity(intent);
            }
        } else {
            Log.w(TAG, "File selection canceled or failed with resultCode: " + resultCode);
        }
    }

    private String encryptAndSaveFile(Uri fileUri) {
        try {
            Log.d(TAG, "Encrypting and saving file: " + fileUri.toString());
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            byte[] buffer = new byte[1024];
            int bytesRead;

            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            File encryptedFile = new File(getFilesDir(), "encrypted_" + System.currentTimeMillis());

            EncryptedFile encrypted = new EncryptedFile.Builder(
                    encryptedFile,
                    this,
                    masterKeyAlias,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build();

            try (FileOutputStream fos = encrypted.openFileOutput()) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            Log.d(TAG, "File encrypted successfully: " + encryptedFile.getAbsolutePath());
            return encryptedFile.getAbsolutePath();
        } catch (IOException | GeneralSecurityException e) {
            Log.e(TAG, "Failed to encrypt and save file: " + fileUri.toString(), e);
            setPopup.showError(this, "Failed to encrypt and save file", null);
            return null;
        }
    }
}