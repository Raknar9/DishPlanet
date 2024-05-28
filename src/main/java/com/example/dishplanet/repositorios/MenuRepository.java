package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.entidades.Pedido;
import com.example.dishplanet.entidades.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {

    @Query("SELECT m FROM Menu m ORDER BY m.vecesPedidas DESC")
    List<Menu> findAllByOrderByVecesPedidasDesc();

    List<Menu> findTop3ByOrderByVecesPedidasDesc();

    Optional<Menu> findByNombre(String nombre);
}
