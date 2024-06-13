package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.Reserva;
import com.example.dishplanet.servicios.ReservaService;
import com.example.dishplanet.servicios.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador ReservaController para manejar las solicitudes relacionadas con las reservas.
 * Este controlador proporciona funcionalidades para hacer, consultar y cancelar reservas.
 */
@Controller
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UserService userService;

    /**
     * Maneja las solicitudes GET a la URL "/reservas".
     * Muestra el formulario de reserva.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista de reserva
     */
    @GetMapping("/reservas")
    public String mostrarFormularioReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "reservas/reserva";
    }

    /**
     * Maneja las solicitudes POST a la URL "/reservas".
     * Intenta hacer una reserva con los datos proporcionados.
     *
     * @param reserva los datos de la reserva
     * @param model el modelo al cual se añaden los atributos
     * @return redirige a la vista de reserva si hay un error, o a la vista de reservas en caso de éxito
     */
    @PostMapping("/reservas")
    public String hacerReserva(@ModelAttribute Reserva reserva, Model model) {
        boolean exito = reservaService.hacerReservaPosible(reserva);
        if (!exito) {
            model.addAttribute("error", "No hay disponibilidad en esta fecha y hora");
            return "reservas/reserva";
        }
        return "redirect:/reservas";
    }

    /**
     * Maneja las solicitudes GET a la URL "/consultar-reservas".
     * Muestra todas las reservas existentes.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista de consulta de reservas
     */
    @GetMapping("/consultar-reservas")
    public String mostrarConsultarReservas(Model model) {
        List<Reserva> reservas = reservaService.obtenerTodasReservas();
        for (Reserva reserva : reservas) {
            reserva.setUsuario(userService.obtenerUsuarioPorId(reserva.getUsuario().getId()));
        }
        model.addAttribute("reservas", reservas);
        return "reservas/consulta";
    }

    /**
     * Maneja las solicitudes POST a la URL "/cancelar-reserva".
     * Cancela una reserva con el ID proporcionado.
     *
     * @param id el ID de la reserva a cancelar
     * @return redirige a la vista de consulta de reservas
     */
    @PostMapping("/cancelar-reserva")
    public String cancelarReserva(@RequestParam Long id) {
        reservaService.cancelarReserva(id);
        return "redirect:/consultar-reservas";
    }

    /**
     * Maneja las solicitudes GET a la URL "/buscar-reservas".
     * Busca reservas por el nombre del usuario.
     *
     * @param usuario el nombre del usuario
     * @return una lista de reservas correspondientes al usuario
     */
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