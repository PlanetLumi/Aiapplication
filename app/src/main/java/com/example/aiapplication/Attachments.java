package com.example.aiapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import android.widget.ListView;
import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.MasterKeys;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public class Attachments {

    private Context context;
    public static final int REQUEST_PERMISSION_CODE = 100;
    public static final int REQUEST_IMAGE_CAPTURE = 101;
    public static final int REQUEST_SELECT_FILE = 102;

    public Attachments(Context context) {
        this.context = context;
    }

    public static void requestPermissions(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE
            );
        }
    }


    public void openCamera(Activity activity) {
        File photoFile = new File(activity.getExternalFilesDir(null), "photo_" + System.currentTimeMillis() + ".jpg");
        Uri photoUri = androidx.core.content.FileProvider.getUriForFile(activity, "com.example.aiapplication.fileprovider", photoFile);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); // Specify where to save the photo

        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(activity, "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    public void openFilePicker(Activity activity, String mimeType) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mimeType); // Allow all files: "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_SELECT_FILE);
    }

    public void showOptionDialog(Activity activity) {
        // Inflate the custom layout
        android.view.LayoutInflater inflater = activity.getLayoutInflater();
        android.view.View dialogView = inflater.inflate(setPalette.setLayout(context, "option_picker"), null);

        // Find the buttons in the custom layout as ImageButton
        android.widget.ImageButton btnCamera = dialogView.findViewById(R.id.camera);
        android.widget.ImageButton btnLibrary = dialogView.findViewById(R.id.files);

        // Build the dialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setView(dialogView); // Set the custom layout as the dialog's content

        // Create the dialog
        androidx.appcompat.app.AlertDialog dialog = builder.create();

        // Configure the window for dim background
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); // Set transparent background
            dialog.getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND); // Enable dim background
            dialog.getWindow().setDimAmount(0.8f); // Set dim amount (adjust 0.0 - 1.0 for effect)
        }

        // Set button click listeners
        btnCamera.setOnClickListener(v -> {
            openCamera(activity); // Open the camera
            dialog.dismiss(); // Close the dialog
        });

        btnLibrary.setOnClickListener(v -> {
            openFilePicker(activity, "*/*"); // Allow all file types
            dialog.dismiss(); // Close the dialog
        });

        // Show the dialog
        dialog.show();
    }
}
