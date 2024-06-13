package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.servicios.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


/**
 * Controlador PanelController para manejar las solicitudes relacionadas con el panel de administración.
 * Este controlador proporciona funcionalidades para crear y eliminar menús y platos, así como generar informes de pedidos.
 */
@Controller
@RequestMapping("/panel")
public class PanelController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private PlatoService platoService;

    /**
     * Maneja las solicitudes GET a la URL "/panel/menu/nuevo".
     * Muestra el formulario para crear un nuevo menú.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista del formulario de nuevo menú
     */
    @GetMapping("/menu/nuevo")
    public String mostrarFormularioNuevoMenu(Model model) {
        model.addAttribute("menu", new Menu());
        return "nuevos/nuevoMenu";
    }

    /**
     * Maneja las solicitudes POST a la URL "/panel/menu/eliminar".
     * Elimina un menú basado en el nombre del menú.
     *
     * @param nombreMenu el nombre del menú a eliminar
     * @return redirige a la página de menús
     */
    @PostMapping("/menu/eliminar")
    public String eliminarMenu(@RequestParam String nombreMenu) {
        menuService.deleteByNombre(nombreMenu);
        return "redirect:/menu/menus";
    }

    /**
     * Maneja las solicitudes POST a la URL "/panel/pedido/informePedidos".
     * Genera un informe de pedidos y lo envía en la respuesta HTTP.
     *
     * @param response la respuesta HTTP para enviar el informe
     * @throws IOException si ocurre un error al generar el informe
     */
    @PostMapping("/pedido/informePedidos")
    public void generarInformePedidos(HttpServletResponse response) throws IOException {
        detallePedidoService.generarInformePedidos(response);
    }

    /**
     * Maneja las solicitudes GET a la URL "/panel/plato/nuevo".
     * Muestra el formulario para crear un nuevo plato.
     *
     * @return el nombre de la vista del formulario de nuevo plato
     */
    @GetMapping("/plato/nuevo")
    public String mostrarFormularioNuevoPlato() {
        return "nuevos/nuevoPlato";
    }

    /**
     * Maneja las solicitudes POST a la URL "/panel/plato/eliminar".
     * Elimina un plato basado en el nombre del plato.
     *
     * @param nombre el nombre del plato a eliminar
     * @return redirige a la página de platos principales
     */
    @PostMapping("/plato/eliminar")
    public String eliminarPlato(@RequestParam String nombre) {
        platoService.deleteByNombre(nombre);
        return "redirect:/plato/principales";
    }
}
