package com.example.ToDoList.security;

import com.example.ToDoList.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private final String SECRET_KEY = "DKxXsbfwgoDrVQG89xUn65HTY4aA6XGE"; // Cambia esto por una clave más segura
    private final long EXPIRATION_TIME = 86400000; // 1 día en milisegundos

    public String generateToken(Users users) {
        Claims claims = Jwts.claims().setSubject(users.getEmail());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
