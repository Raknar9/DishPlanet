package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlatoRepository extends JpaRepository<Plato, Long> {

    @Query("SELECT p FROM Plato p WHERE p.tipo = 'Postre'")
    List<Plato> findAllPostres();

    @Query("SELECT p FROM Plato p WHERE p.tipo = 'Principal'")
    List<Plato> findAllPrincipal();

    @Query("SELECT p FROM Plato p WHERE p.tipo = 'Bebida'")
    List<Plato> findAllBebida();

    @Query("SELECT p FROM Plato p WHERE p.tipo = 'Entrante'")
    List<Plato> findAllEntrante();

    Optional<Plato> findByNombre(String nombre);

    void deleteByNombre(String nombre);

    boolean existsByNombre(String nombre);
}