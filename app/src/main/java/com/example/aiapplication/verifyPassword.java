package com.example.aiapplication;

public class verifyPassword {
    public static boolean verify(String passwords) {
        String[] passwordSet = passwords.split(",");
        return passwordSet[0].replace(",", "").equals(passwordSet[1].replace(",",""));

    }
}
