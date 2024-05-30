package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Plato;
import com.example.dishplanet.repositorios.PlatoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class PlatoService {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private IngredienteUsadoService ingredienteUsadoService ;

    public List<Plato> getAllPlatos() {
        return platoRepository.findAll();
    }
    public void savePlatos(Plato plato) {
        platoRepository.save(plato);
    }
    public List<Plato> obtenerPostres() {
        return platoRepository.findAllPostres();
    }

    public List<Plato> obtenerBebidas() {
        return platoRepository.findAllBebida();
    }

    public List<Plato> obtenerEntrantes() {
        return platoRepository.findAllEntrante();
    }

    public List<Plato> obtenerPrincipales() {
        return platoRepository.findAllPrincipal();
    }

    public Optional<Plato> buscarPorId(Long id) {
        Optional plato=platoRepository.findById(id);
        return plato;
    }
    @Transactional
    public Plato savePlato(Plato plato) {
        Plato savedPlato = platoRepository.save(plato);
        ingredienteUsadoService.saveIngredientesUsados(savedPlato);
        return savedPlato;
    }
    @Transactional
    public void deleteByNombre(String nombre) {
        Optional<Plato> plato = platoRepository.findByNombre(nombre);
       // log.info("'borra el plato "+plato.get().getNombre());
        plato.ifPresent(platoRepository::delete);
    }
    public boolean existsByNombre(String nombre) {
        return platoRepository.existsByNombre(nombre);
    }
}