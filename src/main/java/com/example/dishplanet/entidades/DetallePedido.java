package com.example.dishplanet.entidades;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Detalle_Pedido")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id_detalle;
    @Column(name = "nombres_platos")
    private String nombres_platos;
    @Column(name = "precio_total")
    private double precio_total;
    @Column(name = "Usuario")
    private String NombreUsuario;
    @Column(name = "id_usuario")
    private Long id_usuario;
}
