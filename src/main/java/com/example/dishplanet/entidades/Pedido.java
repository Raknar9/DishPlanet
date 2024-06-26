package com.example.dishplanet.entidades;

import jakarta.persistence.*;
import lombok.*;


/**
 * Clase que representa los pedidos en el sistema.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Pedido")
    private Long id;

    @Column(name = "id_plato")
    private Long idPlato;
    @Column(name = "id_menu")
    private Long idMenu;

    @Column(name = "nombre_plato")
    private String nombrePlato;

    @Column(name = "precio")
    private double precio;


}
