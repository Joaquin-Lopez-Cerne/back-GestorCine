package com.uade.ecommerce.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaDTO {
    private Long id;
    private String titulo;
    private String genero;
    private Integer duracion;
}
