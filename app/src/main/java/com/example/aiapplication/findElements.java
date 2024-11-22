package com.example.aiapplication;
public class findElements {
    public static String findSubject(String text) {
        if (text == null || text.isEmpty()) {
            return "Null";
        }
        String[] lines = text.split("\\n");
        return lines[0];
    }
}



