package com.example.ToDoList.security;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[24]; // Genera 24 bytes
        secureRandom.nextBytes(randomBytes);
        String secretKey = Base64.getEncoder().encodeToString(randomBytes); // Convierte a Base64
        System.out.println("Generated SECRET_KEY: " + secretKey);
    }
}
