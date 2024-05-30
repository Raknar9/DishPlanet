package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.entidades.Pedido;
import com.example.dishplanet.entidades.Plato;
import com.example.dishplanet.servicios.MenuService;
import com.example.dishplanet.servicios.PedidoService;
import com.example.dishplanet.servicios.PlatoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Slf4j
@Controller
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("plato")
public class PlatoController {

    private static final String UPLOAD_DIR = "src/main/resources/static/img/";


    @Autowired
    private PlatoService platoService;
    @Autowired
    private MenuService menuService;


    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("topMenus", menuService.getTopMenus());
        List<Menu> topMenus = menuService.getTopMenus();
        log.info("index");
        log.info("top "+ topMenus);
        return "index";
    }


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
                                    @RequestParam("tipo") String tipo) throws IOException {


        // Guardar el plato en la base de datos con la ruta de la imagen
        Plato plato = new Plato();
        plato.setNombre(nombre);
        plato.setDescripcion(descripcion);
        plato.setPrecio(precio);
        plato.setTipo(tipo);
        plato.setImagen("/img/new.jpg" ); // Ruta relativa a la imagen
        platoService.savePlatos(plato);

        return "redirect:/plato/principales";
    }


}
