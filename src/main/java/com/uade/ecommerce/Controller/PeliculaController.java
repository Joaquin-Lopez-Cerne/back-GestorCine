package com.uade.ecommerce.Controller;

import com.uade.ecommerce.Model.Pelicula;
import com.uade.ecommerce.Service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "*")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public List<Pelicula> listar() {
        return peliculaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Pelicula buscarPorId(@PathVariable Long id) {
        return peliculaService.obtenerPorId(id).orElse(null);
    }

    @PostMapping
    public Pelicula crear(@RequestBody Pelicula pelicula) {
        return peliculaService.guardar(pelicula);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        peliculaService.eliminar(id);
    }
}