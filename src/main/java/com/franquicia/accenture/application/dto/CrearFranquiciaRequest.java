package com.franquicia.accenture.application.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CrearFranquiciaRequest {
    private String nombre;
    private String ciudad;
    private List<SucursalRequest> sucursales;
}
