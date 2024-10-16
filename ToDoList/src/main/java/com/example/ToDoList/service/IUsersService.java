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
    List<Users> findByAll();
    Users delete(Integer id);
    LoginResponse login(LoginRequest request);
    boolean validateToken(String token);
    Users findByEmail(String email);
}
