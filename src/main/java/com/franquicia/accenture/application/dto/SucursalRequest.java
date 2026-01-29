package com.franquicia.accenture.application.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SucursalRequest {
    private String nombre;
    private String ciudad;
    private List<ProductoRequest> productos;
}
