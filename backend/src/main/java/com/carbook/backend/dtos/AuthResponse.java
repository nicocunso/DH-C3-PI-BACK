package com.carbook.backend.dtos;

import com.carbook.backend.entities.RolUsuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    @Enumerated(EnumType.ORDINAL)
    private RolUsuario rol;
}
