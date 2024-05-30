package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Inventario;
import com.example.dishplanet.entidades.Menu;
import com.example.dishplanet.repositorios.InventarioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private EmailService emailService;

    public List<Inventario> obtenerInventario() {
        return inventarioRepository.findAll();
    }
    public void guardarInventario(Inventario inventario) {
        inventarioRepository.save(inventario);
    }

    public void actualizarCantidad(Long id, int cantidad) {
        Inventario inventario = inventarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        inventario.setCantidad(cantidad);
        inventarioRepository.save(inventario);
    }
    @Transactional
    public void updateInventario(String nombreIngrediente) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findByNombre(nombreIngrediente);
        if (inventarioOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            inventario.setCantidad(inventario.getCantidad() - 1);
            inventarioRepository.save(inventario);
        }
    }
    @Transactional
    public void deleteByNombre(String nombre) {
        Optional<Inventario> inventario = inventarioRepository.findByNombre(nombre);
        // log.info("'borra el plato "+plato.get().getNombre());
        inventario.ifPresent(inventarioRepository::delete);
    }
    @Transactional
    public void revisarYActualizarInventario() {
        List<Inventario> items = inventarioRepository.findAll();

        StringBuilder mensaje = new StringBuilder();
        boolean enviarAlerta = false;

        for (Inventario item : items) {
            if (item.getCantidad() <= 20) {
                enviarAlerta = true;
                int cantidadReponer = 50;
                item.setCantidad(item.getCantidad() + cantidadReponer);
                double costoTotal = cantidadReponer * item.getPrecioUnitario();

                mensaje.append(String.format("El item %s llegó a %d unidades!!.\n____________\n Se repuso con %d unidades más.\n________________\n Costo total de la reposición:€ %.2f\n",
                        item.getNombre(), 20, cantidadReponer, costoTotal));
            }
        }

        if (enviarAlerta) {
            inventarioRepository.saveAll(items);
            emailService.sendEmail("alejanbenitez.002@gmail.com", "Alerta de Inventario", mensaje.toString());
        }
    }
    public boolean existsByNombre(String nombre) {
        return inventarioRepository.existsByNombre(nombre);
    }
}
