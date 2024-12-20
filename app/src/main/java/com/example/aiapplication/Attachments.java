package com.example.aiapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            setPopup.showError(activity, "No camera app found", null);
        }
    }

    public void openFilePicker(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_SELECT_FILE);
    }

    public void showOptionDialog(Activity activity) {
        String[] options = {"Camera", "Library"};
        new androidx.appcompat.app.AlertDialog.Builder(activity)
                .setTitle("Choose an option")
                .setItems(options, (dialog, choose) -> {
                    if (choose == 0) {
                        openCamera(activity);
                    } else if (choose == 1) {
                        openFilePicker(activity);
                    }
                })
                .show();
    }
}