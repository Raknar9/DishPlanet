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
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuCotroller {
    @Autowired
    private MenuService menuService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private IngredienteUsadoService ingredienteUsadoService;



    @GetMapping("/menus")
    public String showMenuPage(Model model) {
        model.addAttribute("menus", menuService.getAllMenus());
        return "platos/menus";
    }

    @PostMapping("/menus")
    public String addMenu(@RequestParam("menuId") Long menuId, Model model, HttpServletResponse response) {
        Menu menu = menuService.getMenuById(menuId);
        if (menu != null) {
            Pedido pedido = new Pedido();
            pedido.setIdPlato(menu.getIdMenu());
            pedido.setNombrePlato(menu.getNombre());
            pedido.setPrecio(menu.getPrecio());

            // Save the pedido
            pedidoService.guardarPedido(pedido);

            // Increment the vecesPedidas count
            menuService.incrementMenuCount(menuId);
            ingredienteUsadoService.saveIngredientesUsadosMenu(menu);
            inventarioService.revisarYActualizarInventario();
            //
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
    @PostMapping("/guardar")
    public String guardarMenu(@ModelAttribute("menu") Menu menu) {
        menuService.guardarMenu(menu);
        return "redirect:/menu/menus";
    }


}
