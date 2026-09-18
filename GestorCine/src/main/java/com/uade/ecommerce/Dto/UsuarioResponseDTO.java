package com.uade.ecommerce.Dto;

import com.uade.ecommerce.Model.Sexo;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private LocalDate fechaNacimiento;
    private Sexo sexo;
}