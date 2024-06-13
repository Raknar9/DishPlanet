package com.example.dishplanet.controladores;


import com.example.dishplanet.entidades.Plato;
import com.example.dishplanet.servicios.PlatoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controlador PlatoController para manejar las solicitudes relacionadas con los platos.
 * Este controlador proporciona funcionalidades para mostrar y guardar platos.
 */
@Slf4j
@Controller
@RequestMapping("/plato")
public class PlatoController {

    @Value("${uploads.directory}")
    private String uploadsDirectory;

    @Autowired
    private PlatoService platoService;

    /**
     * Maneja las solicitudes GET a la URL "/plato/postres".
     * Muestra todos los postres disponibles.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista de postres
     */
    @GetMapping("/postres")
    public String mostrarPostres(Model model) {
        model.addAttribute("platos", platoService.obtenerPostres());
        return "platos/postres";
    }

    /**
     * Maneja las solicitudes GET a la URL "/plato/entrantes".
     * Muestra todos los entrantes disponibles.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista de entrantes
     */
    @GetMapping("/entrantes")
    public String mostrarEntrantes(Model model) {
        model.addAttribute("platos", platoService.obtenerEntrantes());
        return "platos/entrantes";
    }

    /**
     * Maneja las solicitudes GET a la URL "/plato/principales".
     * Muestra todos los platos principales disponibles.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista de platos principales
     */
    @GetMapping("/principales")
    public String mostrarPrincipales(Model model) {
        model.addAttribute("platos", platoService.obtenerPrincipales());
        return "platos/principales";
    }

    /**
     * Maneja las solicitudes GET a la URL "/plato/bebidas".
     * Muestra todas las bebidas disponibles.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista de bebidas
     */
    @GetMapping("/bebidas")
    public String mostrarBebidas(Model model) {
        model.addAttribute("platos", platoService.obtenerBebidas());
        return "platos/bebidas";
    }

    /**
     * Maneja las solicitudes POST a la URL "/plato/guardar".
     * Guarda un nuevo plato en la base de datos y almacena su imagen en la carpeta 'uploads'.
     *
     * @param nombre el nombre del plato
     * @param descripcion la descripción del plato
     * @param precio el precio del plato
     * @param ingredientes los ingredientes del plato
     * @param tipo el tipo del plato (postre, entrante, principal, bebida)
     * @param imagen la imagen del plato
     * @return redirige a la página de platos principales
     * @throws IOException si ocurre un error al guardar la imagen
     */
    @PostMapping("/guardar")
    public String guardarNuevoPlato(@RequestParam("nombre") String nombre,
                                    @RequestParam("descripcion") String descripcion,
                                    @RequestParam("precio") double precio,
                                    @RequestParam("ingredientes") String ingredientes,
                                    @RequestParam("tipo") String tipo,
                                    @RequestParam("imagen") MultipartFile imagen) throws IOException {
        // Guardar el plato en la base de datos
        Plato plato = new Plato();
        plato.setNombre(nombre);
        plato.setDescripcion(descripcion);
        plato.setIngredientes(ingredientes);
        plato.setPrecio(precio);
        plato.setTipo(tipo);
        // Guardar la imagen en la carpeta 'uploads'
        if (!imagen.isEmpty()) {
            String fileName = System.currentTimeMillis() + "-" + imagen.getOriginalFilename();
            Path path = Paths.get(uploadsDirectory + fileName);
            Files.write(path, imagen.getBytes());
            plato.setImagen("/uploads/" + fileName); // Ruta relativa a la imagen
        }
        platoService.savePlatos(plato);
        return "redirect:/plato/principales";
    }
}