package com.franquicia.accenture.infrastructure.persistence.mapper;

import com.franquicia.accenture.domain.model.Producto;
import com.franquicia.accenture.infrastructure.persistence.entity.ProductoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductoEntityMapper {

    public ProductoEntity toEntity(Producto domain) {
        if (domain == null) {
            return null;
        }
        ProductoEntity entity = new ProductoEntity();
        entity.setIdProducto(domain.getIdProducto());
        entity.setNombreProducto(domain.getNombreProducto());
        entity.setPrecio(domain.getPrecio());
        entity.setCantidadProducto(domain.getCantidadProducto());
        entity.setSucursalId(domain.getSucursalId());
        return entity;
    }

    public Producto toDomain(ProductoEntity entity) {
        if (entity == null) {
            return null;
        }
        Producto domain = new Producto();
        domain.setIdProducto(entity.getIdProducto());
        domain.setNombreProducto(entity.getNombreProducto());
        domain.setPrecio(entity.getPrecio());
        domain.setCantidadProducto(entity.getCantidadProducto());
        domain.setSucursalId(entity.getSucursalId());
        return domain;
    }
}
