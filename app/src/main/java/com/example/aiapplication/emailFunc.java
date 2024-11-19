package com.example.aiapplication;

import static android.content.Intent.createChooser;

import android.content.Intent;
import android.os.Bundle;



public class emailFunc {
    public static void sendEmail(String DomainEmail, String Subject, String body){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{DomainEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, Subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        createChooser(intent,"Choose Email Client");
    }
}
