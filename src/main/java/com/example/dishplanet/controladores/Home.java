package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.servicios.MenuService;
import com.example.dishplanet.servicios.PlatoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
public class Home {
    @Autowired
    private MenuService menuService;


    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("topMenus", menuService.getTopMenus());

        return "index";
    }

    @GetMapping("/panel")
    public String panel(Model model) {

        return "nuevos/controlPanel";
    }
}
