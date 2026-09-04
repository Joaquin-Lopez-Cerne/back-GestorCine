package com.uade.ecommerce.Service;

import com.uade.ecommerce.Model.Usuario;
import com.uade.ecommerce.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizar(Long id, Usuario datosActualizados) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(datosActualizados.getNombre());
            usuario.setEmail(datosActualizados.getEmail());

            if (datosActualizados.getPeliculas() != null) {
                usuario.setPeliculas(datosActualizados.getPeliculas());
            }

            if (datosActualizados.getPeliculasFavoritas() != null) {
                usuario.setPeliculasFavoritas(datosActualizados.getPeliculasFavoritas());
            }

            return usuarioRepository.save(usuario);
        });
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
