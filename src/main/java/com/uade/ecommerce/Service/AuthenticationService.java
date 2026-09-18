package com.uade.ecommerce.Service;

import com.uade.ecommerce.Dto.LoginRequest;
import com.uade.ecommerce.Dto.RegisterRequest;
import com.uade.ecommerce.Exception.ArgumentInvalidException;
import com.uade.ecommerce.Model.Role;
import com.uade.ecommerce.Model.Usuario;
import com.uade.ecommerce.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Concentra el registro y el inicio de sesión de los usuarios del cine.
 * La contraseña se persiste cifrada y nunca forma parte de los DTO de respuesta.
 */
@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        if (request == null) {
            throw new ArgumentInvalidException("Los datos de registro son obligatorios");
        }
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new ArgumentInvalidException("El nombre del usuario no puede estar vacío");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ArgumentInvalidException("El email del usuario no puede estar vacío");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ArgumentInvalidException("La contraseña no puede estar vacía");
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ArgumentInvalidException("Ya existe un usuario con ese email");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setFechaNacimiento(request.getFechaNacimiento());
        usuario.setSexo(request.getSexo());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        // El alta pública nunca permite que el cliente se autoasigne ADMIN.
        usuario.setRole(Role.USER);
        usuarioRepository.save(usuario);

        return "Usuario registrado correctamente";
    }

    public Authentication authenticate(LoginRequest request) {
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new ArgumentInvalidException("Email y contraseña son obligatorios");
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));
    }
}
