package com.example.aiapplication;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class hashingAlg {
    public static String hashPassword(String password) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = algorithm.digest(password.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b:messageDigest){
                hex.append(String.format("%02x", 0xff & b));
            }
            return hex.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String saltGen(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String saltHash(String password, String salt){
        return hashPassword(password + salt);
    }
}
