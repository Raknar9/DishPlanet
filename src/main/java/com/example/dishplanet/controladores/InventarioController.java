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
import java.util.List;

/**
 * Controlador InventarioController para manejar las solicitudes relacionadas con el inventario.
 * Este controlador proporciona funcionalidades para listar, buscar, actualizar, crear y eliminar inventarios.
 */
@Slf4j
@Controller
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    /**
     * Maneja las solicitudes GET a la URL "/inventario".
     * Lista los inventarios, y si se proporciona un término de búsqueda, realiza una búsqueda de inventarios.
     *
     * @param model el modelo al cual se añaden los atributos
     * @param search el término de búsqueda opcional
     * @return el nombre de la vista del inventario
     */
    @GetMapping("/inventario")
    public String listarInventarios(Model model, @RequestParam(required = false) String search) {
        List<Inventario> inventarios;
        if (search != null && !search.isEmpty()) {
            inventarios = inventarioService.buscarInventarios(search);
        } else {
            inventarios = inventarioService.listarInventarios();
        }
        model.addAttribute("inventarios", inventarios);
        return "inventario/inventario";
    }

    /**
     * Maneja las solicitudes POST a la URL "/inventario/actualizar".
     * Actualiza la cantidad de un inventario específico.
     *
     * @param id el id del inventario a actualizar
     * @param cantidad la nueva cantidad del inventario
     * @return redirige a la página del inventario
     */
    @PostMapping("/inventario/actualizar")
    public String actualizarInventario(@RequestParam("id") Long id, @RequestParam("cantidad") int cantidad) {
        log.info("id del item a actualizar " + id);
        log.info("cantidad a actualizar " + cantidad);
        inventarioService.actualizarCantidad(id, cantidad);
        return "redirect:/inventario";
    }

    /**
     * Maneja las solicitudes GET a la URL "/inventario/nuevo".
     * Muestra el formulario para crear un nuevo inventario.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista del formulario de nuevo inventario
     */
    @GetMapping("/inventario/nuevo")
    public String mostrarFormularioNuevoInventario(Model model) {
        model.addAttribute("inventario", new Inventario());
        return "nuevos/nuevoInventario";
    }

    /**
     * Maneja las solicitudes POST a la URL "/inventario/guardar".
     * Guarda un nuevo inventario.
     *
     * @param inventario el inventario a guardar
     * @return redirige a la página del inventario
     */
    @PostMapping("/inventario/guardar")
    public String guardarInventario(@ModelAttribute Inventario inventario) {
        inventarioService.guardarInventario(inventario);
        return "redirect:/inventario";
    }

    /**
     * Maneja las solicitudes POST a la URL "/eliminar".
     * Elimina un inventario basado en el nombre del item.
     *
     * @param nombreItem el nombre del item a eliminar
     * @return redirige a la página del inventario
     */
    @PostMapping("/eliminar")
    public String eliminarItem(@RequestParam String nombreItem) {
        inventarioService.deleteByNombre(nombreItem);
        return "redirect:/inventario";
    }
}