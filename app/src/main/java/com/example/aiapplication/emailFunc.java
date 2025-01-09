package com.example.aiapplication;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class emailFunc {
    //Builds the email to be sent from chosen client
    public static void sendEmail(Activity activity, String domainEmail, String subject, String body, @Nullable List<Uri> attachmentUris) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE); // Use ACTION_SEND_MULTIPLE for multiple attachments
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("*/*"); //Allows all files to be sent
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{domainEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        //If attachment added put in extra
        if (attachmentUris != null && !attachmentUris.isEmpty()) {
            for (Uri uri : attachmentUris) {
                Log.d("AttachmentUri", "Attaching file: " + uri.toString());
            }
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, new ArrayList<>(attachmentUris));
        }
        //Allows to chose client as popup
        Intent chooser = Intent.createChooser(intent, "Choose Email Client");
        activity.startActivity(chooser);
    }
}
