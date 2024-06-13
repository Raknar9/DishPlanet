package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.repositorios.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de menús.
 */
@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    /**
     * Obtiene todos los menús disponibles.
     *
     * @return Lista de todos los menús.
     */
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    /**
     * Obtiene un menú por su ID.
     *
     * @param id ID del menú a buscar.
     * @return Menú encontrado o null si no existe.
     */
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    /**
     * Incrementa el contador de veces que un menú ha sido pedido.
     *
     * @param id ID del menú a actualizar.
     */
    public void incrementMenuCount(Long id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu != null) {
            menu.setVecesPedidas(menu.getVecesPedidas() + 1);
            menuRepository.save(menu);
        }
    }

    /**
     * Obtiene los 3 menús más pedidos.
     *
     * @return Lista de los 3 menús más pedidos.
     */
    public List<Menu> getTopMenus() {
        return menuRepository.findTop3ByOrderByVecesPedidasDesc();
    }

    /**
     * Guarda un nuevo menú en la base de datos.
     *
     * @param menu Menú a ser guardado.
     */
    public void guardarMenu(Menu menu) {
        menu.setVecesPedidas(0);
        menuRepository.save(menu);
    }

    /**
     * Elimina un menú por su nombre.
     *
     * @param nombre Nombre del menú a eliminar.
     */
    @Transactional
    public void deleteByNombre(String nombre) {
        Optional<Menu> menu = menuRepository.findByNombre(nombre);
        menu.ifPresent(menuRepository::delete);
    }

    /**
     * Verifica si existe un menú con el nombre especificado.
     *
     * @param nombre Nombre del menú a verificar.
     * @return `true` si existe un menú con el nombre especificado, `false` de lo contrario.
     */
    public boolean existsByNombre(String nombre) {
        return menuRepository.existsByNombre(nombre);
    }
}

