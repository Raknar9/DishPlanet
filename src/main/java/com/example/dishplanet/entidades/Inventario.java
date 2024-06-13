package com.example.dishplanet.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Clase que representa del inventario en el sistema.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede tener más de 50 caracteres")
    @Column(name = "categoria", nullable = false)
    private String categoria;

    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Size(max = 20, message = "La unidad de medida no puede tener más de 20 caracteres")
    @Column(name = "unidad_medida", nullable = false)
    private String unidadMedida;

    @Positive(message = "El precio unitario debe ser positivo")
    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;
}

