package com.example.ToDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token; // El token de acceso generado
    private String message; // Mensaje de respuesta (opcional)
}
