package com.franquicia.accenture.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("PRODUCTO")
@Data
public class Producto {

    @Id
    Long idProducto;

    String nombreProducto;

    BigDecimal precio;

    Integer cantidadProducto;

    Long sucursalId;
}
