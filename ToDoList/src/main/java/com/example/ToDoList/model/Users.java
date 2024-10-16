package com.example.ToDoList.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity

@Table (name = "usuarios")
public class Users {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String nombre;

    @Column
    private String email;

    @Column
    private String password;

    @Column (name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany (mappedBy = "usuarios", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tasks> tareas;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

}
