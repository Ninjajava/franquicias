package com.franquicia.accenture.dto;


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
