package com.uade.ecommerce.DTO;

import com.uade.ecommerce.Model.Sexo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequestDTO {
    private String nombre;
    private String email;
    private LocalDate fechaNacimiento;
    private Sexo sexo;
}
