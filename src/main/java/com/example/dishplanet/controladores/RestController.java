package com.example.dishplanet.controladores;

import com.example.dishplanet.servicios.InventarioService;
import com.example.dishplanet.servicios.MenuService;
import com.example.dishplanet.servicios.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private PlatoService platoService;
    @Autowired
    private InventarioService inventarioService;
    @GetMapping("/item/existe")
    @ResponseBody
    public Map<String, Boolean> existeItem(@RequestParam String nombreItem) {
        boolean exists = inventarioService.existsByNombre(nombreItem);
        return Collections.singletonMap("existe", exists);
    }

    @GetMapping("/plato/existe")
    @ResponseBody
    public Map<String, Boolean> existePlato(@RequestParam String nombre) {
        boolean exists = platoService.existsByNombre(nombre);
        return Collections.singletonMap("existe", exists);
    }

    @GetMapping("/menu/existe")
    @ResponseBody
    public Map<String, Boolean> existeMenu(@RequestParam String nombreMenu) {
        boolean exists = menuService.existsByNombre(nombreMenu);
        return Collections.singletonMap("existe", exists);
    }
}
