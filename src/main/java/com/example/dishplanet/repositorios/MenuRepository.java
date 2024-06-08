package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findTop3ByOrderByVecesPedidasDesc();

    Optional<Menu> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
