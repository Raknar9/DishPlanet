package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.Inventario;
import com.example.dishplanet.servicios.InventarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Slf4j
@Controller
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/inventario")
    public String mostrarInventario(Model model) {
        model.addAttribute("inventarios", inventarioService.obtenerInventario());
        return "inventario/inventario";
    }

    @PostMapping("/inventario/actualizar")
    public String actualizarInventario(@RequestParam("id") Long id, @RequestParam("cantidad") int cantidad) {
        log.info("id del item a actualizar "+ id);
        log.info("cantidad a actualizar "+ cantidad);
        inventarioService.actualizarCantidad(id, cantidad);
        return "redirect:/inventario";
    }

    @GetMapping("/inventario/nuevo")
    public String mostrarFormularioNuevoInventario(Model model) {
        model.addAttribute("inventario", new Inventario());
        return "nuevos/nuevoInventario";
    }

    @PostMapping("/inventario/guardar")
    public String guardarInventario(@ModelAttribute Inventario inventario) {
        inventarioService.guardarInventario(inventario);
        return "redirect:/inventario";
    }
    @PostMapping("/eliminar")
    public String eliminarItem(@RequestParam String nombreItem) {
        inventarioService.deleteByNombre(nombreItem);
        return "redirect:/inventario";
    }
}