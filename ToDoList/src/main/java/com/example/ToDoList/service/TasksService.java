package com.example.ToDoList.service;

import com.example.ToDoList.model.Tasks;
import com.example.ToDoList.model.Users;
import com.example.ToDoList.repository.TasksRepository;
import com.example.ToDoList.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

public class TasksService implements ITasksService{

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Tasks create(Tasks tasks) {
        return tasksRepository.save(tasks);
    }

    @Override
    public Tasks update(Tasks tasks) {
        // Verificar si la tarea existe
        Optional<Tasks> existingTask = tasksRepository.findById(tasks.getId());
        if (existingTask.isPresent()) {
            // Actualizar los campos necesarios
            Tasks taskToUpdate = existingTask.get();
            taskToUpdate.setTitulo(tasks.getTitulo());
            taskToUpdate.setDescripcion(tasks.getDescripcion());
            taskToUpdate.setUsuarios(tasks.getUsuarios()); // Asigna el usuario
            return tasksRepository.save(taskToUpdate);
        } else {
            throw new EntityNotFoundException("Task not found");
        }
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

    /* La razón principal para usar @Transactional junto con saveAll
    es asegurar que la operación se maneje como una transacción completa.
    Si una de las inserciones falla, todas se desharán
     */
    @Transactional
    public List<Tasks> createBatch(List<Tasks> tasksList, Integer userId) {
        Users usuario = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")); // Manejar el caso de usuario no encontrado

        for (Tasks task : tasksList) {
            task.setUsuarios(usuario); // Asociar el usuario a cada tarea
        }

        return tasksRepository.saveAll(tasksList); // Guardar todas las tareas de una vez
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
