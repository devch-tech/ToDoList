package com.example.ToDoList.security;

import com.example.ToDoList.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKeyString; // Mantenerlo como String

    private byte[] secretKey; // Agregar este atributo

    @Value("${jwt.expiration}")
    private long expirationTime;

    @PostConstruct
    public void init() {
        this.secretKey = Base64.getDecoder().decode(secretKeyString); // Decodifica a byte[]
    }

    // Método para generar un token JWT
    public String generateToken(Users users) {
        Claims claims = Jwts.claims().setSubject(users.getEmail()).build();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // Método para validar el token
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException e) {
            // Registrar la excepción para facilitar la depuración
            System.err.println("Token validation error: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener el correo del usuario a partir del token
    public String getUserEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // Devuelve el correo del usuario
        } catch (JwtException e) {
            // Registrar la excepción para facilitar la depuración
            System.err.println("Error retrieving user email from token: " + e.getMessage());
            return null;
        }
    }
}
