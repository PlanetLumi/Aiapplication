package com.example.aiapplication;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class emailFunc {
    public static void sendEmail(Activity activity, String domainEmail, String subject, String body, @Nullable List<Uri> attachmentUris) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE); // Use ACTION_SEND_MULTIPLE for multiple attachments
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{domainEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        if (attachmentUris != null && !attachmentUris.isEmpty()) {
            for (Uri uri : attachmentUris) {
                Log.d("AttachmentUri", "Attaching file: " + uri.toString());
            }
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, new ArrayList<>(attachmentUris));
        }
        Intent chooser = Intent.createChooser(intent, "Choose Email Client");
        activity.startActivity(chooser);
    }
}
