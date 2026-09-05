package com.uade.ecommerce.Controller;

import com.uade.ecommerce.Model.Usuario;
import com.uade.ecommerce.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.uade.ecommerce.Dto.UsuarioRequestDTO;
import com.uade.ecommerce.Dto.UsuarioResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
       UsuarioResponseDTO usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity <UsuarioResponseDTO> crear(@RequestBody UsuarioRequestDTO usuarioDTO) {
        UsuarioResponseDTO nuevoUsuario = usuarioService.guardar(usuarioDTO);
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(nuevoUsuario);
    }

    @PutMapping("/{id}")
public ResponseEntity<UsuarioResponseDTO> actualizar(
        @PathVariable Long id,
        @RequestBody UsuarioRequestDTO usuarioDTO) {

    return usuarioService.actualizar(id, usuarioDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}
