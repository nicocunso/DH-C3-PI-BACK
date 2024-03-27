package com.carbook.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "autos")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String matricula;

    @Column
    private String modelo;

    @Column
    private Integer anno;

    @Column(name = "tipo_combustible")
    private String tipoCombustible;

    /*
    @Column
    private String transmision;
     */

    @Column
    private Integer kilometraje;

    @Column(name = "precio_x_dia")
    private Double precioXDia;

    @Column
    private String estado;

    @Column(name = "numero_puertas")
    private Integer numeroPuertas;

    @Column(name = "aire_acondicionado")
    private Integer aireAcondicionado;
    /*
    @Column
    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Imagen> imagenes;
    */

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "id_tipo_auto")
    private TipoAuto tipo;

//    @JsonBackReference
    @Column
    @OneToMany(mappedBy="autos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reserva> reservas;
}
