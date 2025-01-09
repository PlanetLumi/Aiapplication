package com.example.aiapplication;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class hashingAlg {
    //Encrypts the password
    public static String hashPassword(String password) {
        try {
            //Standard SHA hashing algorithm
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            //Converts password to bytes
            byte[] messageDigest = algorithm.digest(password.getBytes());
            //Converts bytes to hex
            StringBuilder hex = new StringBuilder();
            //Adds hex to string
            for (byte b:messageDigest){
                hex.append(String.format("%02x", 0xff & b));
            }
            //Return encrypted value as plain string
            return hex.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Generates random salt
    public static String saltGen(){
        //Uses secure random to generate salt as to not be reversed
        SecureRandom random = new SecureRandom();
        //Salt stored as 16 bit byte value
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        //Converts salt to base 64 string
        return Base64.getEncoder().encodeToString(salt);
    }

    //PUts them together for final value
    public static String saltHash(String password, String salt){
        return hashPassword(password + salt);
    }
}
