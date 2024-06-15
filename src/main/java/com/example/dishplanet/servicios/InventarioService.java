package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Inventario;
import com.example.dishplanet.repositorios.InventarioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión del inventario de productos.
 */
@Slf4j
@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Guarda un nuevo inventario en la base de datos.
     *
     * @param inventario El inventario a ser guardado.
     */
    public void guardarInventario(Inventario inventario) {
        inventarioRepository.save(inventario);
    }

    /**
     * Actualiza la cantidad de un producto en el inventario.
     *
     * @param id       El ID del inventario a actualizar.
     * @param cantidad La nueva cantidad del producto.
     * @throws RuntimeException Si no se encuentra el inventario con el ID especificado.
     */
    public void actualizarCantidad(Long id, int cantidad) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        inventario.setCantidad(cantidad);
        inventarioRepository.save(inventario);
    }

    /**
     * Actualiza la cantidad de un ingrediente en el inventario al disminuir en 1 unidad.
     *
     * @param nombreIngrediente El nombre del ingrediente a actualizar.
     */
    @Transactional
    public void updateInventario(String nombreIngrediente) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findByNombre(nombreIngrediente);
        if (inventarioOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            inventario.setCantidad(inventario.getCantidad() - 1);
            inventarioRepository.save(inventario);
            messagingTemplate.convertAndSend("/topic/inventario", inventario);
        }
    }

    /**
     * Elimina un inventario por su nombre.
     *
     * @param nombre El nombre del inventario a eliminar.
     */
    @Transactional
    public void deleteByNombre(String nombre) {
        Optional<Inventario> inventario = inventarioRepository.findByNombre(nombre);
        inventario.ifPresent(item -> {
            inventarioRepository.delete(item);
            messagingTemplate.convertAndSend("/topic/inventario", item);
        });
    }

    /**
     * Revisa y actualiza el inventario, enviando alertas si la cantidad de algún producto es baja.
     * Se aumenta la cantidad de productos bajo cierta condición.
     */
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

                mensaje.append(String.format("El item %s llegó a %d unidades.\nSe repuso con %d unidades más.\nCosto total de la reposición: %.2f\n\n",
                        item.getNombre(), 20, cantidadReponer, costoTotal));
                messagingTemplate.convertAndSend("/topic/inventario", item);
            }
        }

        if (enviarAlerta) {
            inventarioRepository.saveAll(items);
            // Suponiendo que hay un servicio de correo configurado
            emailService.sendEmail("alejaben.990@gmail.com", "Alerta de Inventario", mensaje.toString());
        }
    }

    /**
     * Obtiene una lista de todos los inventarios disponibles.
     *
     * @return Lista de inventarios.
     */
    public List<Inventario> listarInventarios() {
        return inventarioRepository.findAll();
    }

    /**
     * Busca inventarios por nombre o categoría que contengan el término de búsqueda especificado.
     *
     * @param search Término de búsqueda.
     * @return Lista de inventarios que coinciden con el criterio de búsqueda.
     */
    public List<Inventario> buscarInventarios(String search) {
        return inventarioRepository.findByNombreContainingIgnoreCaseOrCategoriaContainingIgnoreCase(search, search);
    }

    /**
     * Verifica si existe un inventario con el nombre especificado.
     *
     * @param nombre Nombre del inventario a verificar.
     * @return `true` si existe un inventario con el nombre especificado, `false` de lo contrario.
     */
    public boolean existsByNombre(String nombre) {
        return inventarioRepository.existsByNombre(nombre);
    }
}
