package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio JPA para la entidad Pedido.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre objetos Pedido en la base de datos.
 */
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
