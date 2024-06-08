package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.fechaHora = :fechaHora")
    int contarReservasEnFechaHora(LocalDateTime fechaHora);

    List<Reserva> findReservasByUsuario_UsernameContainingIgnoreCase(String username);
}
