package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * Repositorio JPA para la entidad Menu.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre objetos Menu en la base de datos.
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findTop3ByOrderByVecesPedidasDesc();

    Optional<Menu> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
