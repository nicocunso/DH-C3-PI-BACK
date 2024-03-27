package com.carbook.backend.dtos;

import com.carbook.backend.entities.Reserva;
import com.carbook.backend.entities.RolUsuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleUsuarioDto {
    private String nombre;
    private String apellido;
    private String email;
    private Set<Reserva> reservas;
}
