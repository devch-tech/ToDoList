package com.example.ToDoList.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Usa el nuevo enfoque para deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users", "/users/batch", "/users/login").permitAll() // Permitir estos endpoints sin autenticación
                        .anyRequest().authenticated() // Requiere autenticación para los demás endpoints
                )
                .httpBasic(Customizer.withDefaults()); // Para permitir autenticación básica si es necesario

        return http.build();
    }
}
