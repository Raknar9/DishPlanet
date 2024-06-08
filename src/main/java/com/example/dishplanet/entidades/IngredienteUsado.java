package com.example.dishplanet.entidades;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ingredientes_Usados")
public class IngredienteUsado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingredienteUsado")
    private Long id_ingredienteUsado;
    @Column(name = "id_plato")
    private Long id_Plato;
    @Column(name = "id_menu")
    private Long id_Menu;
    @Column(name = "ingredientes")
    private String ingredientes;

}
