package com.franquicia.accenture.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CrearProductoRequest {
    private String nombre;
    private BigDecimal precio;
    private Integer cantidad;
}
