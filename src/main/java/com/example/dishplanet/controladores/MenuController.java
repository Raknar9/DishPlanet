package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.entidades.Pedido;
import com.example.dishplanet.servicios.IngredienteUsadoService;
import com.example.dishplanet.servicios.InventarioService;
import com.example.dishplanet.servicios.MenuService;
import com.example.dishplanet.servicios.PedidoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Controlador MenuController para manejar las solicitudes relacionadas con los menús.
 * Este controlador proporciona funcionalidades para mostrar, agregar y guardar menús.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private IngredienteUsadoService ingredienteUsadoService;

    /**
     * Maneja las solicitudes GET a la URL "/menu/menus".
     * Muestra la página de menús.
     *
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista de menús
     */
    @GetMapping("/menus")
    public String showMenuPage(Model model) {
        model.addAttribute("menus", menuService.getAllMenus());
        return "platos/menus";
    }

    /**
     * Maneja las solicitudes POST a la URL "/menu/menus".
     * Añade un menú al pedido, guarda el pedido y actualiza el inventario.
     * También crea una cookie con la información del pedido.
     *
     * @param menuId el id del menú a añadir
     * @param response la respuesta HTTP para añadir la cookie
     * @return redirige a la página de menús
     */
    @PostMapping("/menus")
    public String addMenu(@RequestParam("menuId") Long menuId, HttpServletResponse response) {
        Menu menu = menuService.getMenuById(menuId);
        if (menu != null) {
            Pedido pedido = new Pedido();
            pedido.setIdMenu(menu.getIdMenu());
            pedido.setNombrePlato(menu.getNombre());
            pedido.setPrecio(menu.getPrecio());
            pedidoService.guardarPedido(pedido);
            menuService.incrementMenuCount(menuId);
            ingredienteUsadoService.saveIngredientesUsadosMenu(menu);
            inventarioService.revisarYActualizarInventario();
            try {
                // Codificar el valor de la cookie
                String cookieValue = menuId + "|" + menu.getNombre() + "|" + menu.getPrecio();
                String encodedCookieValue = URLEncoder.encode(cookieValue, "UTF-8");
                Cookie cookie = new Cookie("pedidoPrincipal_" + menuId, encodedCookieValue);
                cookie.setPath("/");
                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 días
                response.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                // Manejar el error de codificación
            }
        }
        return "redirect:/menu/menus";
    }

    /**
     * Maneja las solicitudes POST a la URL "/menu/guardar".
     * Guarda un nuevo menú.
     *
     * @param menu el menú a guardar
     * @return redirige a la página de menús
     */
    @PostMapping("/guardar")
    public String guardarMenu(@ModelAttribute("menu") Menu menu) {
        menuService.guardarMenu(menu);
        return "redirect:/menu/menus";
    }
}
