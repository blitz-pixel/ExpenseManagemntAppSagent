package com.example.ExpenseManagementApp.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// Class to hash passwords in the place of not implementing JWT token
public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean verifyPassword(String rawPassword, String storedHash) {
        return encoder.matches(rawPassword, storedHash);
    }


}

