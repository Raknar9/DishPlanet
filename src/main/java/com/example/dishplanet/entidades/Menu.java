package com.example.dishplanet.entidades;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "entrante")
    private String entrante;

    @Column(name = "principal")
    private String  principal;

    @Column(name = "postre")
    private String  postre;

    @Column(name = "bebida")
    private String  bebida;

    @Column(name = "veces_Pedidas")
    private int vecesPedidas;

    @Column(name = "precio")
    private double precio;

    @Column(name = "ingredientes")
    private String ingredientes;

}