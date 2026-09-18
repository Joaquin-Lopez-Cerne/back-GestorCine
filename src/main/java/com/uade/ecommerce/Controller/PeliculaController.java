package com.uade.ecommerce.Controller;

import com.uade.ecommerce.Dto.PeliculaDTO;
import com.uade.ecommerce.Dto.PeliculaUpdateDTO;
import com.uade.ecommerce.Service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "*")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<List<PeliculaDTO>> listar() {
        return ResponseEntity.ok(peliculaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(peliculaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PeliculaDTO> crear(@RequestBody PeliculaDTO peliculaDTO) {
        PeliculaDTO peliculaGuardada = peliculaService.guardar(peliculaDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(peliculaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> actualizar(
            @PathVariable Long id,
            @RequestBody PeliculaUpdateDTO peliculaDTO) {

        return ResponseEntity.ok(peliculaService.actualizar(id, peliculaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        peliculaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
