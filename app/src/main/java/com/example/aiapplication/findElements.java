package com.example.aiapplication;
public class findElements {
    //Finds the subject of the email within ChatGPT response
    public static String findSubject(String text) {
        if (text == null || text.isEmpty()) {
            return "Null";
        }
        String[] lines = text.split("\\n");
        return lines[0];
    }
}



