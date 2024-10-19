package com.example.ToDoList.security;

import com.example.ToDoList.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private byte[] secretKey;  // Cambiar a byte array

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(Users users) {
        Claims claims = Jwts.claims().setSubject(users.getEmail()).build();


        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)  // Usar secretKey como byte array
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)  // Usar secretKey como byte array
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException e) {
            // Registrar la excepción para facilitar la depuración
            System.err.println("Token validation error: " + e.getMessage());
            return false;
        }
    }
}