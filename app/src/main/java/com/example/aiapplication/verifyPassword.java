package com.example.aiapplication;
public class verifyPassword {
    //Verify password and retype password parameters match
    public static boolean verifyMatch(String[] passwords) {
        return passwords[0].equals(passwords[1]);
    }

    //Verify password parameters within String array
    public static String[] allFlags(String password) {
        return new String[]{verifyLength(password), verifyUpper(password), verifyLower(password), verifySymbol(password), verifyNum(password), verifyNotSimple(password)};
    }
    //Verify password is not simple
    public static String verifyNotSimple(String password) {
        if(!password.toLowerCase().contains("password")){
            return "True";
        } else{
            return "Password must not contain the word 'password'";
        }
    }
    //Verify password length is > 8
    public static String verifyLength(String password) {
        if (password.length() < 8) {
            return "Password must be at least 8 characters";
        }
        return "True";
    }

    //verify there is atleast one uppercase letter
    public static String verifyUpper(String password) {
        for (int i = 0; i < password.length(); i++) {
            //Checks each letter and if true drops out of loop
            if (Character.isUpperCase(password.charAt(i))){
                return "True";
            }
        }
        return "Password must contain at least one uppercase letter";
    }
    //Verify there is atleast one lowercase letter
    public static String verifyLower(String password) {
        for (int i = 0; i < password.length(); i++) {
            //Checks each letter and if true drops out of loop
            if (Character.isLowerCase(password.charAt(i))) {
                return "True";
            }
        }
        return "Password must contain at least one lowercase letter";
    }
    //Verify there is atleast one special character
    public static String verifySymbol(String password) {
        for (int i = 0; i < password.length(); i++) {
            //Checks each character and if there is a symbol then drops out of loop
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                return "True";
            }
        }
        return "Password must contain at least one special character (e.g !@#$%^&*)";
    }
    //Verify there is atleast one number
    public static String verifyNum(String password){
        for (int i = 0; i < password.length(); i++) {
            //Checks each character and if there is a number then drops out of loop
            if (Character.isDigit(password.charAt(i))) {
                return "True";
            }
        }
        return "Password must contain at least one number";
    }
}
