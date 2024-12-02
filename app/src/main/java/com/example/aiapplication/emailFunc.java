package com.example.aiapplication;
import android.app.Activity;
import android.content.Intent;



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
