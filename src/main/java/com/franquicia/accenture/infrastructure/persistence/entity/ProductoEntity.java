package com.franquicia.accenture.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("PRODUCTO")
@Data
public class ProductoEntity {

    @Id
    Long idProducto;
    String nombreProducto;
    BigDecimal precio;
    Integer cantidadProducto;
    Long sucursalId;
}
