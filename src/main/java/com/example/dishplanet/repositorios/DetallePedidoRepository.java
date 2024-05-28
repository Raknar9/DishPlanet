package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.DetallePedido;
import com.example.dishplanet.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido,Long> {
}
