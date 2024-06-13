package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio JPA para la entidad Reserva.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre objetos Reserva en la base de datos.
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.fechaHora = :fechaHora")
    int contarReservasEnFechaHora(LocalDateTime fechaHora);

    List<Reserva> findReservasByUsuario_UsernameContainingIgnoreCase(String username);
}
