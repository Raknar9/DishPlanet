package com.example.dishplanet.controladores;


import com.example.dishplanet.servicios.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




/**
 * Controlador Home para manejar solicitudes web.
 * Este controlador gestiona las páginas de inicio y del panel de control.
 * Utiliza el MenuService para obtener datos del menú.
 */
@Slf4j
@Controller
public class Home {

    @Autowired
    private MenuService menuService;

    /**
     * Maneja las solicitudes GET a la URL raíz ("/").
     * Añade los menús principales al modelo y devuelve la vista del índice.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista del índice
     */
    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("topMenus", menuService.getTopMenus());
        return "index";
    }

    /**
     * Maneja las solicitudes GET a la URL "/panel".
     * Devuelve la vista del panel de control.
     *
     * @return el nombre de la vista del panel de control
     */
    @GetMapping("/panel")
    public String panel() {
        return "nuevos/controlPanel";
    }
}