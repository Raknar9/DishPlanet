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

@Slf4j
@Controller
@RequestMapping("/plato")
public class PlatoController {
    @Value("${uploads.directory}")
    private String uploadsDirectory;
    @Autowired
    private PlatoService platoService;

    @GetMapping("/postres")
    public String mostrarPostres(Model model) {
        model.addAttribute("platos", platoService.obtenerPostres());
        return "platos/postres";
    }

    @GetMapping("/entrantes")
    public String mostrarEntrantes(Model model) {
        model.addAttribute("platos", platoService.obtenerEntrantes());
        return "platos/entrantes";
    }

    @GetMapping("/principales")
    public String mostrarPrincipales(Model model) {
        model.addAttribute("platos", platoService.obtenerPrincipales());
        return "platos/principales";
    }

    @GetMapping("/bebidas")
    public String mostrarBebidas(Model model) {
        model.addAttribute("platos", platoService.obtenerBebidas());
        return "platos/bebidas";
    }

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
