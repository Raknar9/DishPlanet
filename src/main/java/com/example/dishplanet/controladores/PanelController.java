package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.entidades.Pedido;
import com.example.dishplanet.entidades.Plato;
import com.example.dishplanet.servicios.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/panel")
public class PanelController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private PlatoService platoService;
    @Autowired
    private UserService userService;

    @GetMapping("/menu/nuevo")
    public String mostrarFormularioNuevoMenu(Model model) {

        model.addAttribute("menu", new Menu());
        return "nuevos/nuevoMenu";
    }

    @PostMapping("/menu/eliminar")
    public String eliminarMenu(@RequestParam String nombreMenu) {

        menuService.deleteByNombre(nombreMenu);
        return "redirect:/menu/menus";
    }

    @GetMapping("/pedido/informePedidos")
    public void generarInformePedidos(HttpServletResponse response) throws IOException {
        detallePedidoService.generarInformePedidos(response);
    }

    @GetMapping("/plato/nuevo")
    public String mostrarFormularioNuevoPlato() {

        return "nuevos/nuevoPlato";
    }

    @PostMapping("/plato/eliminar")
    public String eliminarPlato(@RequestParam String nombre) {

        platoService.deleteByNombre(nombre);
        return "redirect:/plato/principales";
    }


}
