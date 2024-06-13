package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio JPA para la entidad DetallePedido.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre objetos DetallePedido en la base de datos.
 */
public interface DetallePedidoRepository extends JpaRepository<DetallePedido,Long> {
}
