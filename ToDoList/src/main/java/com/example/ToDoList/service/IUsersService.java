package com.example.ToDoList.service;

import com.example.ToDoList.dto.LoginRequest;
import com.example.ToDoList.dto.LoginResponse;
import com.example.ToDoList.model.Users;
import org.apache.catalina.User;
import java.util.List;

public interface IUsersService {
    Users create (Users user);
    Users update (Users user);
    Users findById (Integer id);
    Users findByEmail(String email);
    List<Users> findAll();
    Users delete(Integer id);
    Users register(Users newUser);
    LoginResponse login(LoginRequest request);
    boolean validateToken(String token);
}
