package com.carbook.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaUsuarioResponse {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaDevolucion;
    private Long idAuto;
}