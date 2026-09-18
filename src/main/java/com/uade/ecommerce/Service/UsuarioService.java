package com.uade.ecommerce.Service;

import com.uade.ecommerce.Dto.UsuarioRequestDTO;
import com.uade.ecommerce.Dto.UsuarioResponseDTO;
import com.uade.ecommerce.Exception.ArgumentInvalidException;
import com.uade.ecommerce.Exception.ResourceNotFoundException;
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

    public UsuarioResponseDTO obtenerPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado con id: " + id
                        )
                );

        return convertirAResponseDTO(usuario);
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO usuarioDTO) {

        if (usuarioDTO.getNombre() == null || usuarioDTO.getNombre().isBlank()) {
            throw new ArgumentInvalidException(
                    "El nombre del usuario no puede estar vacío"
            );
        }

        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isBlank()) {
            throw new ArgumentInvalidException(
                    "El email del usuario no puede estar vacío"
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setSexo(usuarioDTO.getSexo());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return convertirAResponseDTO(usuarioGuardado);
    }

    private UsuarioResponseDTO convertirAResponseDTO(Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getFechaNacimiento(),
                usuario.getSexo()
        );
    }

    public Optional<UsuarioResponseDTO> actualizar(
            Long id,
            UsuarioRequestDTO datosActualizados) {

        return usuarioRepository.findById(id).map(usuario -> {

            usuario.setNombre(datosActualizados.getNombre());
            usuario.setEmail(datosActualizados.getEmail());
            usuario.setFechaNacimiento(datosActualizados.getFechaNacimiento());
            usuario.setSexo(datosActualizados.getSexo());

            Usuario usuarioActualizado = usuarioRepository.save(usuario);

            return convertirAResponseDTO(usuarioActualizado);
        });
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}