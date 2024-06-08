package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.repositorios.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    public void incrementMenuCount(Long id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu != null) {
            menu.setVecesPedidas(menu.getVecesPedidas() + 1);
            menuRepository.save(menu);
        }
    }

    public List<Menu> getTopMenus() {
        return menuRepository.findTop3ByOrderByVecesPedidasDesc();
    }

    public void guardarMenu(Menu menu) {
        menu.setVecesPedidas(0);
        menuRepository.save(menu);
    }

    @Transactional
    public void deleteByNombre(String nombre) {
        Optional<Menu> menu = menuRepository.findByNombre(nombre);
        // log.info("'borra el plato "+plato.get().getNombre());
        menu.ifPresent(menuRepository::delete);
    }

    public boolean existsByNombre(String nombre) {
        return menuRepository.existsByNombre(nombre);
    }
}

