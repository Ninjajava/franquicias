package com.franquicia.accenture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoMaxStockResponse {
    private String sucursal;
    private String producto;
    private Integer stock;
}
