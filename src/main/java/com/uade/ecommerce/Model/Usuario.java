package com.uade.ecommerce.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private LocalDate fechaNacimiento;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    
    // Relacion OneToMany: un usuario puede tener muchas peliculas.
    @OneToMany
    @JoinColumn(name = "usuario_id")
    private List<Pelicula> peliculas = new ArrayList<>();

    // Relacion ManyToMany: muchos usuarios pueden tener muchas peliculas favoritas.
    @ManyToMany
    @JoinTable(
            name = "usuarios_peliculas_favoritas",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "pelicula_id")
    )
    private List<Pelicula> peliculasFavoritas = new ArrayList<>();
}
