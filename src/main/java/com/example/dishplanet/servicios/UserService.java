package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Usuario;
import com.example.dishplanet.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Random;
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    UsuarioRepository userRepository;
    public Usuario findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<Usuario> getAllUsuarios() {
        return userRepository.findAll();
    }
    public void saveUser(Usuario user) {
        userRepository.save(user);
    }

    public String obtenerNombreUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Nombre de usuario autenticado
        } else {
            return "Invitado"; // Usuario no autenticado
        }
    }
    public Long obtenerIDUsuarioPorNombre(String nombreUsuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            // Si el usuario no está autenticado, devolver null
            return null;
        }

        Optional<Usuario> optionalUsuario = userRepository.findByUsername(nombreUsuario);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            return usuario.getId();
        } else {
            throw new NotFoundException("El usuario con nombre " + nombreUsuario + " no se encontró.");
        }
    }

    public void guardarUsuario(Usuario user) {
        userRepository.save(user);
    }

    public String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
