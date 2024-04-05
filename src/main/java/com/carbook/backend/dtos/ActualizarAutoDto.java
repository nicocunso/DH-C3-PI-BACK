package com.carbook.backend.dtos;

import com.carbook.backend.entities.TipoAuto;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarAutoDto {
    private Long id;
    private Integer kilometraje;
    private Double precioXDia;
    private String estado;
    private Integer aireAcondicionado;
    private TipoAuto tipo;
}

