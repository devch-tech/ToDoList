package com.example.ToDoList.service;

import com.example.ToDoList.dto.LoginRequest;
import com.example.ToDoList.dto.LoginResponse;
import com.example.ToDoList.model.Users;
import com.example.ToDoList.repository.TasksRepository;
import com.example.ToDoList.repository.UsersRepository;
import com.example.ToDoList.security.TokenService;
import io.jsonwebtoken.Jwts;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class UsersService implements IUsersService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;



    @Override
    public Users create(Users users) {
        return usersRepository.save(users);
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

    @Override
    public boolean validateToken(String token) {
        try {
            // Verifica el token y obtiene los claims
            Jwts.parser().setSigningKey(String.valueOf(SECRET_KEY)).parseClaimsJws(token);
            return true; // El token es válido
        } catch (Exception e) {
            // Manejo de errores, el token no es válido
            return false;
        }
    }

    @Override
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
