package com.example.ToDoList.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table (name = "tareas")

public class Tasks {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column (name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn (name = "usuario_id", nullable = false)
    /* @JsonManagedReference y @JsonBackReference: Estas anotaciones
    permiten manejar relaciones de uno a muchos sin causar ciclos */
    @JsonBackReference
    private Users usuarios;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

}

