package com.example.ToDoList.controller;


import com.example.ToDoList.model.Tasks;
import com.example.ToDoList.service.TasksService;
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Tasks tasks){
        Tasks newTasks = tasksService.create(tasks);
        if(newTasks != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(newTasks);
        }else{
            return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR))
                    .body("Hubo un error al procesar su solicitud. Por favor, inténtelo de nuevo más tarde.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Tasks tasks){
        tasksService.update(tasks);
        return new ResponseEntity<>(HttpStatus.OK);
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
