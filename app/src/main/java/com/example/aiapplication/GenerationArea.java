package com.example.aiapplication;
import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.MasterKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class GenerationArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        int layoutId = setPalette.setLayout(GenerationArea.this, "generation_area");
        setContentView(layoutId);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.generationArea), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Retrieve the encrypted file paths from the Intent
        Intent intent = getIntent();
        ArrayList<String> encryptedPaths = intent.getStringArrayListExtra("encryptedFilePaths");

        if (encryptedPaths != null && !encryptedPaths.isEmpty()) {
            Log.d("GenerationArea", "Received encrypted file paths: " + encryptedPaths.toString());
        } else {
            Log.e("GenerationArea", "No encrypted file paths received.");
        }
        List<String> encryptedFilePaths = getIntent().getStringArrayListExtra("encryptedFilePaths");
        if (encryptedFilePaths == null || encryptedFilePaths.isEmpty()) {
            Log.e("GenerationArea", "No encrypted file paths received.");
        } else {
            for (String filePath : encryptedFilePaths) {
                Log.d("GenerationArea", "Received encrypted file path: " + filePath);
            }
        }
        List<Uri> fileUris = new ArrayList<>(); // To store decrypted file URIs

        if (encryptedFilePaths != null && !encryptedFilePaths.isEmpty()) {
            for (String encryptedFilePath : encryptedFilePaths) {
                Log.d("EncryptedFilePath", "Processing file: " + encryptedFilePath);
                Uri fileUri = processEncryptedFile(encryptedFilePath);
                if (fileUri != null) {
                    fileUris.add(fileUri); // Add non-null URI to the list
                } else {
                    Log.e("FileUri", "Failed to process encrypted file: " + encryptedFilePath);
                }
            }
        }

        //Checks to see if prompt for complaint or refund
        String preprompt = findType();
        TextView grabbedEmail = findViewById(R.id.grabbedEmail);
        //Checks users input text file
        String[] requestSplit = ReadData.returnData(GenerationArea.this, "userRequestInput.txt").split(",");

        //Calling for email
        HunterGenCall hunterGenCall = new HunterGenCall();
        hunterGenCall.fetchEmail(this, requestSplit[1], new HunterGenCall.EmailResultCallBack() {
            @Override
            public void onSuccess(String email) {
                grabbedEmail.setText(email);
            }

            @Override
            public void onE(String errorMessage) {
                grabbedEmail.setText("DOMAIN NOT FOUND");
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    //Checks to see if activity is built before displaying error as to not crash
                    if (!isFinishing() && !isDestroyed()) {
                        setPopup.showError(GenerationArea.this, "Email Grab Failed!", "Please try again.");
                    }
                }, 200);}
        });

        TextView generationBox = findViewById(R.id.GenerationBox);
        //Allows drag
        generationBox.setFocusable(true);
        generationBox.setFocusableInTouchMode(true);

        ChatGenCall chatGenCall = new ChatGenCall();
        //main request function
        chatGenCall.generateRequest(GenerationArea.this,preprompt,fileUris, requestSplit[3].replace("]", ""), new ChatGenCall.generateRequestCallBack() {
            @Override
            public void onSuccess(String request) {
                generationBox.setText(request);
                //Checks to find subject of email
                String subject = findElements.findSubject(request);
                ImageButton sendEmail = findViewById(R.id.sendEmail);
                sendEmail.setOnClickListener(v -> {
                    //Testing to see if attachment is being read
                    Log.d("GenerationArea", "Preparing to send email...");
                    if (fileUris.isEmpty()) {
                        Log.e("GenerationArea", "No file URIs to attach.");
                    } else {
                        for (Uri uri : fileUris) {
                            Log.d("GenerationArea", "Attaching URI: " + uri.toString());
                        }
                    }
                    //Fills email with parameters
                    emailFunc.sendEmail(GenerationArea.this, grabbedEmail.getText().toString(), subject, request.replace(subject, ""), fileUris);
                    //Shows popup
                    setPopup.showSuccess(GenerationArea.this, "successbot", "Request sent!", null);
                });
            }

            @Override
            public void onE(String errorMessage) {
                generationBox.setText("Error");
                setPopup.showError(GenerationArea.this, "Generation failed!", "Please try again.");
            }
        });

        ExitButtonFunc.exitBtn(GenerationArea.this, RequestPage.class);
    }

    //Decrypts the files in the saved file paths as URI's
    private Uri processEncryptedFile(String encryptedFilePath) {
        try {
            Log.d("GenerationArea", "Processing encrypted file: " + encryptedFilePath);
            //Saved key
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            File encryptedFile = new File(encryptedFilePath);

            //Builds encryption to REVERSE-cypher text
            EncryptedFile encrypted = new EncryptedFile.Builder(
                    encryptedFile,
                    this,
                    masterKeyAlias,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build();

            //Finds decrypted file and sends it to output stream
            File decryptedFile = new File(getCacheDir(), encryptedFile.getName() + "_decrypted");
            try (FileInputStream fis = encrypted.openFileInput();
                 FileOutputStream fos = new FileOutputStream(decryptedFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            // Generate a URI for the decrypted file
            Uri fileUri = androidx.core.content.FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    decryptedFile
            );
            Log.d("GenerationArea", "Decrypted file saved at: " + decryptedFile.getAbsolutePath());
            Log.d("GenerationArea", "Generated URI for file: " + fileUri.toString());
            return fileUri;
        } catch (IOException | GeneralSecurityException e) {
            Log.e("GenerationArea", "Failed to decrypt and process file: " + encryptedFilePath, e);
            return null;
        }
    }

    private String findType() {
        String generationType = ReadData.returnData(GenerationArea.this, "userChoice.txt");
        if ("1".equals(generationType)) {
            return prePromptArea.prePromptRefund(GenerationArea.this);
        } else {
            return prePromptArea.prePromptComplaints(GenerationArea.this);
        }
    }
}