package com.example.aiapplication;

public class verifyPassword {
    public static boolean verify(String[] passwords) {
        return passwords[0].equals(passwords[1]);
    }
}
