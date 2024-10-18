package com.example.ToDoList.service;

import com.example.ToDoList.model.Tasks;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITasksService {
    Tasks create(Tasks tasks);
    Tasks update(Tasks tasks);
    Tasks findById(Integer id);
    List<Tasks> findAll();
    @Transactional
    List<Tasks> createBatch(List<Tasks> tasksList, Integer userId);
    Tasks delete(Integer id);
}
