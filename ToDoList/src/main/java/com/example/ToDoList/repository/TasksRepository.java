package com.example.ToDoList.repository;

import com.example.ToDoList.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {}
