package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Usuario;
import com.example.dishplanet.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Servicio para la gestión de usuarios.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private UsuarioRepository userRepository;

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario a buscar.
     * @return Usuario encontrado o null si no existe.
     */
    public Usuario findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de todos los usuarios.
     */
    public List<Usuario> getAllUsuarios() {
        return userRepository.findAll();
    }

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param user Usuario a ser guardado.
     */
    public void saveUser(Usuario user) {
        userRepository.save(user);
    }

    /**
     * Obtiene el nombre del usuario autenticado.
     *
     * @return Nombre del usuario autenticado o "Invitado" si no está autenticado.
     */
    public String obtenerNombreUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Nombre de usuario autenticado
        } else {
            return "Invitado"; // Usuario no autenticado
        }
    }

    /**
     * Obtiene el ID del usuario por su nombre de usuario.
     *
     * @param nombreUsuario Nombre de usuario a buscar.
     * @return ID del usuario encontrado o null si no está autenticado.
     */
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

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado.
     * @throws RuntimeException Si no se encuentra el usuario con el ID especificado.
     */
    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOptional = userRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        } else {
            // Maneja el caso donde no se encuentra el usuario
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param user Usuario a ser guardado.
     */
    public void guardarUsuario(Usuario user) {
        userRepository.save(user);
    }

    /**
     * Genera un código de verificación aleatorio de 6 dígitos.
     *
     * @return Código de verificación generado.
     */
    public String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    /**
     * Verifica si existe un usuario con el nombre de usuario especificado.
     *
     * @param username Nombre de usuario a verificar.
     * @return `true` si existe un usuario con el nombre de usuario especificado, `false` de lo contrario.
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
