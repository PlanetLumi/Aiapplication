package com.example.aiapplication;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import java.util.concurrent.Executors;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class RequestPage extends AppCompatActivity {
    private static final String TAG = "AttachmentsActivity";
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private List<String> encryptedFilePaths = new ArrayList<>();
    private Intent generationAreaIntent;

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

        // Initialize the intent for GenerationArea
        generationAreaIntent = new Intent(this, GenerationArea.class);
        generationAreaIntent.putExtra("userRequestInputFilePath", new File(getFilesDir(), "userRequestInput.txt").getAbsolutePath());

        // Configure the save button
        saveButtonFunc.funcSaveBtn(RequestPage.this, RequestPage.this, new String[]{"companyInp", "detailsInp", "usePersonalData"}, "userRequestInput.txt", GenerationArea.class, generationAreaIntent);

        // Configure the exit button
        ExitButtonFunc.exitBtn(RequestPage.this, MainMenu.class);

        // Set up the file picker button
        ImageButton attachBtn = findViewById(R.id.attachButton);
        attachBtn.setOnClickListener(v -> {
            Attachments attach = new Attachments(RequestPage.this);
            Attachments.requestPermissions(RequestPage.this);
            attach.showOptionDialog(RequestPage.this);
        });

        // Initialize the ActivityResultLauncher
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        handleFileSelection(result.getData());
                    } else {
                        Log.w(TAG, "File selection canceled or failed.");
                    }
                }
        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Attachments.REQUEST_IMAGE_CAPTURE) {
                // Handle the captured image
                // Depending on your openCamera logic, you may need to retrieve the saved photo URI
                Uri photoUri = data != null ? data.getData() : null; // Replace this with your saved URI logic
                if (photoUri != null) {
                    encryptAndSaveFileAsync(photoUri, () -> {
                        // Handle UI updates or transition
                        Log.d(TAG, "Photo encrypted and saved successfully.");
                    });
                } else {
                    Log.e(TAG, "No photo URI received.");
                }
            } else if (requestCode == Attachments.REQUEST_SELECT_FILE && data != null) {
                // Handle the selected file
                Uri fileUri = data.getData();
                if (fileUri != null) {
                    encryptAndSaveFileAsync(fileUri, () -> {
                        // Handle UI updates or transition
                        Log.d(TAG, "File encrypted and saved successfully.");
                    });
                } else {
                    Log.e(TAG, "No file URI received.");
                }
            }
        } else {
            Log.w(TAG, "Action canceled or failed.");
            Toast.makeText(this, "Action canceled.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Attachments.REQUEST_PERMISSION_CODE) {
            boolean permissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    permissionsGranted = false;
                    break;
                }
            }

            if (!permissionsGranted) {
                Toast.makeText(this, "Permissions are required to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleFileSelection(Intent data) {
        int totalFiles = data.getClipData() != null ? data.getClipData().getItemCount() : 1;
        int[] completedFiles = {0};

        if (data.getClipData() != null) {
            for (int i = 0; i < totalFiles; i++) {
                Uri fileUri = data.getClipData().getItemAt(i).getUri();
                encryptAndSaveFileAsync(fileUri, () -> {
                    synchronized (completedFiles) {
                        completedFiles[0]++;
                        if (completedFiles[0] == totalFiles) {
                            onFileEncryptionComplete();
                        }
                    }
                });
            }
        } else if (data.getData() != null) {
            Uri fileUri = data.getData();
            encryptAndSaveFileAsync(fileUri, () -> {
                synchronized (completedFiles) {
                    completedFiles[0]++;
                    if (completedFiles[0] == totalFiles) {
                        onFileEncryptionComplete();
                    }
                }
            });
        }
    }

    private void onFileEncryptionComplete() {
        if (!encryptedFilePaths.isEmpty()) {
            generationAreaIntent.putStringArrayListExtra("encryptedFilePaths", new ArrayList<>(encryptedFilePaths));
            Log.d(TAG, "All files encrypted. Transitioning to GenerationArea with paths: " + encryptedFilePaths);

            // Log Intent extras
            Log.d(TAG, "Intent extras: " + generationAreaIntent.getExtras());
        } else {
            Log.e(TAG, "No files were encrypted. Cannot transition to GenerationArea.");
            runOnUiThread(() -> Toast.makeText(this, "No files were encrypted. Please try again.", Toast.LENGTH_SHORT).show());
        }
    }

    private void encryptAndSaveFileAsync(Uri fileUri, Runnable onComplete) {
        Executors.newSingleThreadExecutor().execute(() -> {
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
                synchronized (encryptedFilePaths) {
                    encryptedFilePaths.add(encryptedFile.getAbsolutePath());
                    generationAreaIntent.putStringArrayListExtra("encryptedFilePaths", new ArrayList<>(encryptedFilePaths));
                }
                Log.d(TAG, "Updated encryptedFilePaths list: " + encryptedFilePaths.toString());
            } catch (IOException | GeneralSecurityException e) {
                Log.e(TAG, "Failed to encrypt and save file: " + fileUri.toString(), e);
            } finally {
                runOnUiThread(onComplete); // Update UI or proceed to next step
            }
        });
    }
}