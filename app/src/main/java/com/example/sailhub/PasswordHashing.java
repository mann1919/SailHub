package com.example.sailhub;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
Password hashing using SHA-256
 */
public class PasswordHashing{

    public static String doHashing (String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

}