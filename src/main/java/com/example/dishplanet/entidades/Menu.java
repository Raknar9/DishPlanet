package com.example.dishplanet.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Clase que representa los menus en el sistema.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Menu")
    private Long idMenu;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El entrante es obligatorio")
    @Column(name = "entrante", nullable = false)
    private String entrante;

    @NotBlank(message = "El plato principal es obligatorio")
    @Column(name = "principal", nullable = false)
    private String principal;

    @NotBlank(message = "El postre es obligatorio")
    @Column(name = "postre", nullable = false)
    private String postre;

    @NotBlank(message = "La bebida es obligatoria")
    @Column(name = "bebida", nullable = false)
    private String bebida;

    @PositiveOrZero(message = "Las veces pedidas no pueden ser negativas")
    @Column(name = "veces_Pedidas", nullable = false)
    private int vecesPedidas;

    @Positive(message = "El precio debe ser positivo")
    @Column(name = "precio", nullable = false)
    private double precio;

    @Size(max = 1000, message = "Los ingredientes no pueden tener más de 1000 caracteres")
    @Column(name = "ingredientes")
    private String ingredientes;
}