package com.example.ToDoList.service;

import com.example.ToDoList.dto.LoginRequest;
import com.example.ToDoList.dto.LoginResponse;
import com.example.ToDoList.model.Users;
import com.example.ToDoList.repository.UsersRepository;
import com.example.ToDoList.security.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements IUsersService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;


    @Override
    public Users create(Users user) {
        // Cifrar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    @Override
    public Users update(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public Users findById(Integer id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        return usersOptional.orElse(null);
    }

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Users delete(Integer id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        if(usersOptional.isPresent()){
            Users users = usersOptional.get();
            usersRepository.deleteById(id);
            return users;
        }
        return null;
    }

    @Override
    public Users register(Users newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword())); // Cifrar la contraseña
        return usersRepository.save(newUser); // Guardar el nuevo usuario
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. Obtener el usuario por email
        Users users = usersRepository.findByEmail(request.getEmail());

        // 2. Verificar si el usuario existe y si la contraseña es correcta
        if (users != null && passwordEncoder.matches(request.getPassword(), users.getPassword())) {
            // 3. Generar un token (puedes usar JWT u otra estrategia)
            String token = tokenService.generateToken(users); // Implementa este método para generar el token

            // 4. Crear y devolver la respuesta de login
            return new LoginResponse(token, "Login successful");
        }
        // 5. Devolver null o lanzar una excepción si la autenticación falla
        return null;
    }

    public boolean validateToken(String token) {
        return tokenService.validateToken(token);
    }

    @Override
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
