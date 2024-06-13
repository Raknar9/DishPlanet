package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
/**
 * Repositorio JPA para la entidad Usuario.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre objetos Usuario en la base de datos.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u from Usuario u " +
            "where lower(u.username) = ?1 or lower(u.email) = ?1")
    Optional<Usuario> buscarPorUsernameOEmail(String s);

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);
}
