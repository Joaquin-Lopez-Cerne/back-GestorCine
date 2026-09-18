package com.uade.ecommerce.Service;

import com.uade.ecommerce.Dto.PeliculaDTO;
import com.uade.ecommerce.Dto.PeliculaUpdateDTO;
import com.uade.ecommerce.Exception.ArgumentInvalidException;
import com.uade.ecommerce.Exception.ResourceNotFoundException;
import com.uade.ecommerce.Model.Pelicula;
import com.uade.ecommerce.Repository.PeliculaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<PeliculaDTO> obtenerTodas() {
        return peliculaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public PeliculaDTO obtenerPorId(Long id) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pelicula no encontrada con id: " + id));

        return convertirADTO(pelicula);
    }

    public PeliculaDTO guardar(PeliculaDTO peliculaDTO) {
        if (peliculaDTO == null) {
            throw new ArgumentInvalidException("Los datos de la pelicula son obligatorios");
        }

        validarPelicula(peliculaDTO.getTitulo(), peliculaDTO.getGenero(), peliculaDTO.getDuracion());

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(peliculaDTO.getTitulo());
        pelicula.setGenero(peliculaDTO.getGenero());
        pelicula.setDuracion(peliculaDTO.getDuracion());

        Pelicula peliculaGuardada = peliculaRepository.save(pelicula);
        return convertirADTO(peliculaGuardada);
    }

    public PeliculaDTO actualizar(Long id, PeliculaUpdateDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new ArgumentInvalidException("Los datos de la pelicula son obligatorios");
        }

        validarPelicula(
                datosActualizados.getTitulo(),
                datosActualizados.getGenero(),
                datosActualizados.getDuracion());

        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pelicula no encontrada con id: " + id));

        pelicula.setTitulo(datosActualizados.getTitulo());
        pelicula.setGenero(datosActualizados.getGenero());
        pelicula.setDuracion(datosActualizados.getDuracion());

        Pelicula peliculaActualizada = peliculaRepository.save(pelicula);
        return convertirADTO(peliculaActualizada);
    }

    public void eliminar(Long id) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pelicula no encontrada con id: " + id));

        peliculaRepository.delete(pelicula);
    }

    private PeliculaDTO convertirADTO(Pelicula pelicula) {
        return new PeliculaDTO(
                pelicula.getId(),
                pelicula.getTitulo(),
                pelicula.getGenero(),
                pelicula.getDuracion()
        );
    }

    private void validarPelicula(String titulo, String genero, Integer duracion) {
        if (titulo == null || titulo.isBlank()) {
            throw new ArgumentInvalidException("El titulo de la pelicula no puede estar vacio");
        }

        if (genero == null || genero.isBlank()) {
            throw new ArgumentInvalidException("El genero de la pelicula no puede estar vacio");
        }

        if (duracion == null || duracion <= 0) {
            throw new ArgumentInvalidException("La duracion de la pelicula debe ser mayor a 0");
        }
    }
}
