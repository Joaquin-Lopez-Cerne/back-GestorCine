package com.uade.ecommerce.Service;

import com.uade.ecommerce.Dto.UsuarioRequestDTO;
import com.uade.ecommerce.Dto.UsuarioResponseDTO;
import com.uade.ecommerce.Exception.ArgumentInvalidException;
import com.uade.ecommerce.Exception.ResourceNotFoundException;
import com.uade.ecommerce.Model.Usuario;
import com.uade.ecommerce.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));

        return convertirAResponseDTO(usuario);
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO usuarioDTO) {
        if (usuarioDTO == null) {
            throw new ArgumentInvalidException("Los datos del usuario son obligatorios");
        }

        validarNombreYEmail(usuarioDTO.getNombre(), usuarioDTO.getEmail());
        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isBlank()) {
            throw new ArgumentInvalidException("La contrasena no puede estar vacia");
        }
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new ArgumentInvalidException("Ya existe un usuario con ese email");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setSexo(usuarioDTO.getSexo());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(usuarioGuardado);
    }

    public Optional<UsuarioResponseDTO> actualizar(
            Long id,
            UsuarioRequestDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new ArgumentInvalidException("Los datos del usuario son obligatorios");
        }

        validarNombreYEmail(datosActualizados.getNombre(), datosActualizados.getEmail());

        return usuarioRepository.findById(id).map(usuario -> {
            usuarioRepository.findByEmail(datosActualizados.getEmail())
                    .filter(usuarioConEmail -> !usuarioConEmail.getId().equals(id))
                    .ifPresent(usuarioConEmail -> {
                        throw new ArgumentInvalidException("Ya existe un usuario con ese email");
                    });

            usuario.setNombre(datosActualizados.getNombre());
            usuario.setEmail(datosActualizados.getEmail());
            if (datosActualizados.getPassword() != null && !datosActualizados.getPassword().isBlank()) {
                usuario.setPassword(passwordEncoder.encode(datosActualizados.getPassword()));
            }
            usuario.setFechaNacimiento(datosActualizados.getFechaNacimiento());
            usuario.setSexo(datosActualizados.getSexo());

            Usuario usuarioActualizado = usuarioRepository.save(usuario);
            return convertirAResponseDTO(usuarioActualizado);
        });
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }

        usuarioRepository.deleteById(id);
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

    private void validarNombreYEmail(String nombre, String email) {
        if (nombre == null || nombre.isBlank()) {
            throw new ArgumentInvalidException("El nombre del usuario no puede estar vacio");
        }

        if (email == null || email.isBlank()) {
            throw new ArgumentInvalidException("El email del usuario no puede estar vacio");
        }
    }
}
