package com.example.dishplanet.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;



@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Plato")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plato")
    private Long id_plato;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    @Column(name = "descripcion")
    private String descripcion;

    @Positive(message = "El precio debe ser positivo")
    @Column(name = "precio", nullable = false)
    private double precio;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 50, message = "El tipo no puede tener más de 50 caracteres")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Size(max = 255, message = "La URL de la imagen no puede tener más de 255 caracteres")
    @Column(name = "imagen")
    private String imagen;

    @Size(max = 1000, message = "Los ingredientes no pueden tener más de 1000 caracteres")
    @Column(name = "ingredientes")
    private String ingredientes;
}
