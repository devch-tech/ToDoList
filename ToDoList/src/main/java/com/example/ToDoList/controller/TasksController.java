package com.example.ToDoList.controller;


import com.example.ToDoList.model.Tasks;
import com.example.ToDoList.model.Users;
import com.example.ToDoList.service.TasksService;
import com.example.ToDoList.service.UsersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/batch")
    public ResponseEntity<?> createBatch(@RequestBody List<Tasks> tasksList, @RequestParam Integer userId) {
        Users usuario = usersService.findById(userId);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario especificado no existe.");
        }

        // Asociar el usuario a cada tarea
        for (Tasks task : tasksList) {
            task.setUsuarios(usuario);
        }

        // Guardar todas las tareas de forma batch
        List<Tasks> newTasks = tasksService.createBatch(tasksList, userId);

        if (newTasks != null && !newTasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newTasks);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Hubo un error al procesar su solicitud. Por favor, inténtelo de nuevo más tarde.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Tasks tasks) {
        try {
            Tasks updatedTask = tasksService.update(tasks);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer idTasks){
        Tasks tasks = tasksService.findById(idTasks);
        if(tasks != null){
            return ResponseEntity.status(HttpStatus.OK).body(tasks);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ninguna tarea con el ID proporcionado.");
        }
    }

    @GetMapping
    public ResponseEntity<List<?>> findAll(){
        List<Tasks> tasks = tasksService.findAll();
        if(!tasks.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(tasks);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No se encontraron tareas"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer idTasks){
        Tasks tasks = tasksService.delete(idTasks);
        if(tasks != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("La tarea con id " + idTasks + " se ha eliminado correctamente");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La tarea no existe");
        }
    }

}
