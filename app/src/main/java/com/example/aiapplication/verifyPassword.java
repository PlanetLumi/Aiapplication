package com.example.aiapplication;
public class verifyPassword {

    public static boolean verifyMatch(String[] passwords) {
        return passwords[0].equals(passwords[1]);
    }

    public static String[] allFlags(String password) {
        return new String[]{verifyLength(password), verifyUpper(password), verifyLower(password), verifySymbol(password), verifyNum(password), verifyNotSimple(password)};
    }

    public static String verifyNotSimple(String password) {
        if(!password.toLowerCase().contains("password")){
            return "True";
        } else{
            return "Password must not contain the word 'password'";
        }
    }
    public static String verifyLength(String password) {
        if (password.length() < 8) {
            return "Password must be at least 8 characters";
        }
        return "True";
    }

    public static String verifyUpper(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))){
                return "True";
            }
        }
        return "Password must contain at least one uppercase letter";
    }
    public static String verifyLower(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return "True";
            }
        }
        return "Password must contain at least one lowercase letter";
    }
    public static String verifySymbol(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                return "True";
            }
        }
        return "Password must contain at least one special character (e.g !@#$%^&*)";
    }
    public static String verifyNum(String password){
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return "True";
            }
        }
        return "Password must contain at least one number";
    }
}
