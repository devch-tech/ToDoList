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

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Permitir Swagger
                        .requestMatchers("/users/login", "/users/batch", "/users/register", "/users/validate-token").permitAll() // Permitir login y otros endpoints públicos
                        .anyRequest().authenticated() // Requiere autenticación para el resto de solicitudes
                )
                .formLogin(Customizer.withDefaults()) // Opción de autenticación básica
                .httpBasic(Customizer.withDefaults()); // Añadir autenticación básica

        return http.build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Permitir acceso a Swagger
                        .requestMatchers("/users/login", "/users/batch", "/users/register", "/users/validate-token").permitAll() // Permitir acceso sin autenticación a ciertos endpoints
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                .formLogin(AbstractHttpConfigurer::disable) // Deshabilitar el formulario de inicio de sesión
                .httpBasic(Customizer.withDefaults()); // Añadir autenticación básica

        return http.build();
    }


}