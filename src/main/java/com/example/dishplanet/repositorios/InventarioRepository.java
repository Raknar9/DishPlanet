package com.example.dishplanet.repositorios;


import com.example.dishplanet.entidades.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Inventario> findByNombreContainingIgnoreCaseOrCategoriaContainingIgnoreCase(String nombre, String categoria);
}
