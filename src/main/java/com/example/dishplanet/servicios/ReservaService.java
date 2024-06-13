package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Reserva;
import com.example.dishplanet.entidades.Usuario;
import com.example.dishplanet.repositorios.ReservaRepository;
import com.example.dishplanet.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de reservas.
 */
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository userRepository;

    /**
     * Obtiene todas las reservas almacenadas en la base de datos.
     *
     * @return Lista de todas las reservas.
     */
    public List<Reserva> obtenerTodasReservas() {
        return reservaRepository.findAll();
    }

    /**
     * Cancela una reserva específica por su ID.
     *
     * @param id ID de la reserva a cancelar.
     */
    @Transactional
    public void cancelarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    /**
     * Obtiene todas las reservas realizadas por un usuario específico.
     *
     * @param usuario Nombre de usuario a buscar.
     * @return Lista de reservas realizadas por el usuario especificado.
     */
    public List<Reserva> obtenerReservasPorUsuario(String usuario) {
        return reservaRepository.findReservasByUsuario_UsernameContainingIgnoreCase(usuario);
    }

    /**
     * Realiza una reserva si es posible en la fecha y hora especificadas.
     *
     * @param reserva Reserva a realizar.
     * @return `true` si la reserva se realiza con éxito, `false` si no hay disponibilidad.
     */
    @Transactional
    public boolean hacerReservaPosible(Reserva reserva) {
        // Obtener el nombre de usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();

        // Buscar el usuario autenticado y manejar el Optional
        Optional<Usuario> optionalUsuario = userRepository.findByUsername(nombreUsuario);
        if (!optionalUsuario.isPresent()) {
            throw new IllegalStateException("Usuario no encontrado");
        }
        Usuario usuario = optionalUsuario.get();

        // Asignar el usuario autenticado a la reserva
        reserva.setUsuario(usuario);

        // Lógica para verificar disponibilidad
        LocalDateTime fechaHora = reserva.getFechaHora();
        int reservasEnEsaFechaHora = reservaRepository.contarReservasEnFechaHora(fechaHora);
        if (reservasEnEsaFechaHora >= 5) {
            return false;
        }
        reservaRepository.save(reserva);
        return true;
    }
}

