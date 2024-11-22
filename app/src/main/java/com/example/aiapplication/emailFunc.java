package com.example.aiapplication;

import static android.content.Intent.createChooser;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



public class emailFunc {
    public static void sendEmail(Activity activity, String DomainEmail, String Subject, String body){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{DomainEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, Subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        Intent chooser = Intent.createChooser(intent,"Choose Email Client");
        activity.startActivity(chooser);
    }
}
