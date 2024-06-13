package com.example.dishplanet.controladoresRest;

import com.example.dishplanet.servicios.InventarioService;
import com.example.dishplanet.servicios.MenuService;
import com.example.dishplanet.servicios.PlatoService;
import com.example.dishplanet.servicios.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Controlador RestControllerItemValidator para validar la existencia de ítems, platos, menús y usuarios.
 * Este controlador proporciona endpoints REST para verificar la existencia de elementos en el sistema.
 */
@RestController
public class RestControllerItemValidator {

    @Autowired
    private MenuService menuService;
    @Autowired
    private PlatoService platoService;
    @Autowired
    private InventarioService inventarioService;
    @Autowired
    private UserService usuarioService;

    /**
     * Endpoint REST para verificar si un ítem existe en el inventario.
     *
     * @param nombreItem el nombre del ítem a verificar
     * @return un mapa con la clave "existe" y el valor booleano que indica si existe o no
     */
    @GetMapping("/item/existe")
    @ResponseBody
    public Map<String, Boolean> existeItem(@RequestParam String nombreItem) {
        boolean exists = inventarioService.existsByNombre(nombreItem);
        return Collections.singletonMap("existe", exists);
    }

    /**
     * Endpoint REST para verificar si un plato existe.
     *
     * @param nombre el nombre del plato a verificar
     * @return un mapa con la clave "existe" y el valor booleano que indica si existe o no
     */
    @GetMapping("/plato/existe")
    @ResponseBody
    public Map<String, Boolean> existePlato(@RequestParam String nombre) {
        boolean exists = platoService.existsByNombre(nombre);
        return Collections.singletonMap("existe", exists);
    }

    /**
     * Endpoint REST para verificar si un menú existe.
     *
     * @param nombreMenu el nombre del menú a verificar
     * @return un mapa con la clave "existe" y el valor booleano que indica si existe o no
     */
    @GetMapping("/menu/existe")
    @ResponseBody
    public Map<String, Boolean> existeMenu(@RequestParam String nombreMenu) {
        boolean exists = menuService.existsByNombre(nombreMenu);
        return Collections.singletonMap("existe", exists);
    }

    /**
     * Endpoint REST para verificar si un usuario existe.
     *
     * @param username el nombre de usuario a verificar
     * @return un mapa con la clave "existe" y el valor booleano que indica si existe o no
     */
    @GetMapping("/usuario/existe")
    @ResponseBody
    public Map<String, Boolean> existeUsuario(@RequestParam String username) {
        boolean exists = usuarioService.existsByUsername(username);
        return Collections.singletonMap("existe", exists);
    }
}