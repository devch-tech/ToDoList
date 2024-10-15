package com.example.ToDoList.repository;

import com.example.ToDoList.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {}
