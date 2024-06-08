package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Reserva;
import com.example.dishplanet.entidades.Usuario;
import com.example.dishplanet.repositorios.ReservaRepository;
import com.example.dishplanet.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private UsuarioRepository userRepository;

    public List<Reserva> obtenerTodasReservas() {
        return reservaRepository.findAll();
    }

    public void cancelarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    public List<Reserva> obtenerReservasPorUsuario(String usuario) {
        return reservaRepository.findReservasByUsuario_UsernameContainingIgnoreCase(usuario);
    }

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

