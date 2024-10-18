package com.example.ToDoList.controller;

import com.example.ToDoList.dto.LoginRequest;
import com.example.ToDoList.dto.LoginResponse;
import com.example.ToDoList.model.Users;
import com.example.ToDoList.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Users users){
        Users newUsers = usersService.create(users);
        if(newUsers != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(newUsers);
        }else{
            return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR))
                    .body("Hubo un error al procesar su solicitud. Por favor, inténtelo de nuevo más tarde.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Users users){
        usersService.update(users);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer idUsers){
        Users users = usersService.findById(idUsers);
        if(users != null){
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ningún usuario con el ID proporcionado.");
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email){
        Users users = usersService.findByEmail(email);
        if(users != null){
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ningún usuario con el email proporcionado.");
        }
    }

    @GetMapping
    public ResponseEntity<List<?>> findAll(){
        List<Users> users = usersService.findAll();
        if(!users.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No se encontraron usuarios"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer idUsers){
        Users users = usersService.delete(idUsers);
        if(users != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("El usuario con id " + idUsers + " se ha eliminado correctamente");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El usuario no existe");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Aquí deberías llamar al servicio que maneja la lógica de autenticación
        LoginResponse response = usersService.login(request);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, "Credenciales incorrectas."));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        boolean isValid = usersService.validateToken(token);

        if (isValid) {
            return ResponseEntity.status(HttpStatus.OK).body("Token válido.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido.");
        }
    }

}
