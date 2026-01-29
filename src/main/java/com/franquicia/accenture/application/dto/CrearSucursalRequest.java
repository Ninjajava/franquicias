package com.franquicia.accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CrearSucursalRequest {
    private String nombre;
    private String ciudad;
}
