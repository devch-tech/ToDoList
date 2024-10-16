package com.example.ToDoList.service;

import com.example.ToDoList.model.Tasks;

import java.util.List;

public interface ITasksService {
    Tasks create(Tasks tasks);
    Tasks update(Tasks tasks);
    Tasks findById(Integer id);
    List<Tasks> FindAll();
    Tasks delete(Integer id);
}
