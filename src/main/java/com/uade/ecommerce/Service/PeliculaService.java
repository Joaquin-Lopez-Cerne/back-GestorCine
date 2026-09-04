package com.uade.ecommerce.Service;

import com.uade.ecommerce.Model.Pelicula;
import com.uade.ecommerce.Repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<Pelicula> obtenerTodas() {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> obtenerPorId(Long id) {
        return peliculaRepository.findById(id);
    }

    public Pelicula guardar(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public Optional<Pelicula> actualizar(Long id, Pelicula datosActualizados) {
        return peliculaRepository.findById(id).map(pelicula -> {
            pelicula.setTitulo(datosActualizados.getTitulo());
            pelicula.setGenero(datosActualizados.getGenero());
            pelicula.setDuracion(datosActualizados.getDuracion());
            return peliculaRepository.save(pelicula);
        });
    }

    public void eliminar(Long id) {
        peliculaRepository.deleteById(id);
    }
}
