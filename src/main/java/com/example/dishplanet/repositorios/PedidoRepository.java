package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.Pedido;
import com.example.dishplanet.entidades.Plato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
