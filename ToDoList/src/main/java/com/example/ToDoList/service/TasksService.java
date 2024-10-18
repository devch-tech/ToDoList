package com.example.ToDoList.service;

import com.example.ToDoList.model.Tasks;
import com.example.ToDoList.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TasksService implements ITasksService{

    @Autowired
    private TasksRepository tasksRepository;

    @Override
    public Tasks create(Tasks tasks) {
        return tasksRepository.save(tasks);
    }

    @Override
    public Tasks update(Tasks tasks) {
        return tasksRepository.save(tasks);
    }

    @Override
    public Tasks findById(Integer id) {
        Optional<Tasks> tasksOptional = tasksRepository.findById(id);
        return tasksOptional.orElse(null);
    }

    @Override
    public List<Tasks> findAll() {
        return tasksRepository.findAll();
    }

    @Override
    public Tasks delete(Integer id) {
        Optional<Tasks> tasksOptional = tasksRepository.findById(id);
        if(tasksOptional.isPresent()){
            Tasks tasks = tasksOptional.get();
            tasksRepository.deleteById(id);
            return tasks;
        }
        return null;
    }
}
