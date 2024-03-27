package com.carbook.backend.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "reservas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate fechaInicio;
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate fechaDevolucion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuarios;

 //   @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_auto")
    private Auto autos;


}
