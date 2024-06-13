package com.example.dishplanet.repositorios;

import com.example.dishplanet.entidades.IngredienteUsado;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio JPA para la entidad IngredienteUsado.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre objetos IngredienteUsado en la base de datos.
 */
public interface IngredienteUsadoRepository extends JpaRepository<IngredienteUsado,Long> {
}
