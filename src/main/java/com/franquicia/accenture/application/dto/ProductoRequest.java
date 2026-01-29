package com.franquicia.accenture.application.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoRequest {
    private String nombre;
    private BigDecimal precio;
}
