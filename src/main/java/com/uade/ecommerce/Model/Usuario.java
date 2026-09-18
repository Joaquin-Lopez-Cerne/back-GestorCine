package com.uade.ecommerce.Model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Column(unique = true)
    private String email;
    private LocalDate fechaNacimiento;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    // Datos utilizados para la autenticacion. No se exponen en UsuarioResponseDTO.
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    
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

    @PrePersist
    private void asignarRolPorDefecto() {
        if (role == null) {
            role = Role.USER;
        }
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String nombreRol = role != null ? role.name() : Role.USER.name();
        return List.of(new SimpleGrantedAuthority("ROLE_" + nombreRol));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
