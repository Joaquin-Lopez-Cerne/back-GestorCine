package com.uade.ecommerce.Service;

import com.uade.ecommerce.Dto.LoginRequest;
import com.uade.ecommerce.Dto.RegisterRequest;
import com.uade.ecommerce.Exception.ArgumentInvalidException;
import com.uade.ecommerce.Model.Role;
import com.uade.ecommerce.Model.Usuario;
import com.uade.ecommerce.Repository.UsuarioRepository;
import com.uade.ecommerce.Security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (request == null) {
            throw new ArgumentInvalidException("Los datos de registro son obligatorios");
        }
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new ArgumentInvalidException("El nombre del usuario no puede estar vacio");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ArgumentInvalidException("El email del usuario no puede estar vacio");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ArgumentInvalidException("La contrasena no puede estar vacia");
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ArgumentInvalidException("Ya existe un usuario con ese email");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .fechaNacimiento(request.getFechaNacimiento())
                .sexo(request.getSexo())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        usuarioRepository.save(usuario);
        return "Usuario registrado correctamente";
    }

    public String authenticate(LoginRequest request) {
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new ArgumentInvalidException("Email y contrasena son obligatorios");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ArgumentInvalidException("Usuario no encontrado"));

        Set<String> roles = usuario.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet());

        return jwtUtil.generateToken(usuario.getEmail(), roles);
    }
}
