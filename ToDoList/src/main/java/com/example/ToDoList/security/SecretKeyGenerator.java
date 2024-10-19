package com.example.ToDoList.security;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[64]; // 64 bytes para 512 bits
        secureRandom.nextBytes(randomBytes);
        String secretKey = Base64.getEncoder().encodeToString(randomBytes);
        System.out.println("Generada clave secreta: " + secretKey);
    }
}