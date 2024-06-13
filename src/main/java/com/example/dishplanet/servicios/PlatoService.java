package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Plato;
import com.example.dishplanet.repositorios.PlatoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de platos.
 */
@Slf4j
@Service
public class PlatoService {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private IngredienteUsadoService ingredienteUsadoService;

    /**
     * Obtiene todos los platos almacenados en la base de datos.
     *
     * @return Lista de todos los platos.
     */
    public List<Plato> getAllPlatos() {
        return platoRepository.findAll();
    }

    /**
     * Guarda un plato en la base de datos.
     *
     * @param plato Plato a ser guardado.
     */
    public void savePlatos(Plato plato) {
        platoRepository.save(plato);
    }

    /**
     * Obtiene todos los platos clasificados como postres.
     *
     * @return Lista de platos clasificados como postres.
     */
    public List<Plato> obtenerPostres() {
        return platoRepository.findAllPostres();
    }

    /**
     * Obtiene todos los platos clasificados como bebidas.
     *
     * @return Lista de platos clasificados como bebidas.
     */
    public List<Plato> obtenerBebidas() {
        return platoRepository.findAllBebida();
    }

    /**
     * Obtiene todos los platos clasificados como entrantes.
     *
     * @return Lista de platos clasificados como entrantes.
     */
    public List<Plato> obtenerEntrantes() {
        return platoRepository.findAllEntrante();
    }

    /**
     * Obtiene todos los platos clasificados como principales.
     *
     * @return Lista de platos clasificados como principales.
     */
    public List<Plato> obtenerPrincipales() {
        return platoRepository.findAllPrincipal();
    }

    /**
     * Busca un plato por su ID.
     *
     * @param id ID del plato a buscar.
     * @return Plato encontrado, o vacío si no existe.
     */
    public Optional<Plato> buscarPorId(Long id) {
        return platoRepository.findById(id);
    }

    /**
     * Elimina un plato por su nombre.
     *
     * @param nombre Nombre del plato a eliminar.
     */
    @Transactional
    public void deleteByNombre(String nombre) {
        Optional<Plato> plato = platoRepository.findByNombre(nombre);
        plato.ifPresent(platoRepository::delete);
    }

    /**
     * Verifica si existe un plato con el nombre especificado.
     *
     * @param nombre Nombre del plato a verificar.
     * @return `true` si existe un plato con el nombre especificado, `false` de lo contrario.
     */
    public boolean existsByNombre(String nombre) {
        return platoRepository.existsByNombre(nombre);
    }
}