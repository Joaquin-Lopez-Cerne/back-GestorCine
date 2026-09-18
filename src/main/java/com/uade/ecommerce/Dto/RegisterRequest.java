package com.uade.ecommerce.Dto;

import com.uade.ecommerce.Model.Sexo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Datos necesarios para registrar un usuario que luego pueda autenticarse.
 * Se conservan los campos propios de GestorCine y se agrega password.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    private LocalDate fechaNacimiento;
    private Sexo sexo;
}
