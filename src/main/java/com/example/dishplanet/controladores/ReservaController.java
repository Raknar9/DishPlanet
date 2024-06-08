package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.Reserva;
import com.example.dishplanet.servicios.ReservaService;
import com.example.dishplanet.servicios.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReservaController {
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private UserService userService;

    @GetMapping("/reservas")
    public String mostrarFormularioReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "reservas/reserva";
    }

    @PostMapping("/reservas")
    public String hacerReserva(@ModelAttribute Reserva reserva, Model model) {
        boolean exito = reservaService.hacerReservaPosible(reserva);
        if (!exito) {
            model.addAttribute("error", "No hay disponibilidad en esta fecha y hora");
            return "reservas/reserva";
        }
        return "redirect:/reservas";
    }

    @GetMapping("/consultar-reservas")
    public String mostrarConsultarReservas(Model model) {
        List<Reserva> reservas = reservaService.obtenerTodasReservas();
        for (Reserva reserva : reservas) {
            reserva.setUsuario(userService.obtenerUsuarioPorId(reserva.getUsuario().getId()));
        }
        model.addAttribute("reservas", reservas);
        return "reservas/consulta";
    }

    @PostMapping("/cancelar-reserva")
    public String cancelarReserva(Long id) {
        reservaService.cancelarReserva(id);
        return "redirect:/consultar-reservas";
    }

    @GetMapping("/buscar-reservas")
    @ResponseBody
    public List<Reserva> buscarReservasPorUsuario(@RequestParam("usuario") String usuario) {
        List<Reserva> reservas = reservaService.obtenerReservasPorUsuario(usuario);
        for (Reserva reserva : reservas) {
            reserva.setUsuario(userService.obtenerUsuarioPorId(reserva.getUsuario().getId()));
        }
        return reservas;
    }
}
