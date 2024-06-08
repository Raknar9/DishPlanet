package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.IngredienteUsado;
import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.entidades.Plato;
import com.example.dishplanet.repositorios.IngredienteUsadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteUsadoService {
    @Autowired
    IngredienteUsadoRepository ingredienteUsadoRepository;
    @Autowired
    InventarioService inventarioService;

    @Transactional
    public void saveIngredientesUsados(Plato plato) {
        String[] ingredientes = plato.getIngredientes().split(",");
        for (String ingrediente : ingredientes) {
            IngredienteUsado ingredientesUsados = new IngredienteUsado();
            ingredientesUsados.setIngredientes(ingrediente.trim());
            ingredientesUsados.setId_Plato(plato.getId_plato());
            ingredienteUsadoRepository.save(ingredientesUsados);
            inventarioService.updateInventario(ingrediente.trim());
        }
    }

    @Transactional
    public void saveIngredientesUsadosMenu(Menu Menu) {
        String[] ingredientesM = Menu.getIngredientes().split(",");
        for (String ingrediente : ingredientesM) {
            IngredienteUsado ingredientesUsados = new IngredienteUsado();
            ingredientesUsados.setIngredientes(ingrediente.trim());
            ingredientesUsados.setId_Menu(Menu.getIdMenu());
            ingredienteUsadoRepository.save(ingredientesUsados);
            inventarioService.updateInventario(ingrediente.trim());
        }
    }
}
