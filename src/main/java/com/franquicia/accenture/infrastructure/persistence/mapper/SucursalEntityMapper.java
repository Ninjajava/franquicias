package com.franquicia.accenture.infrastructure.persistence.mapper;

import com.franquicia.accenture.domain.model.Sucursal;
import com.franquicia.accenture.infrastructure.persistence.entity.SucursalEntity;
import org.springframework.stereotype.Component;

@Component
public class SucursalEntityMapper {

    public SucursalEntity toEntity(Sucursal domain) {
        if (domain == null) {
            return null;
        }
        SucursalEntity entity = new SucursalEntity();
        entity.setIdSucursal(domain.getIdSucursal());
        entity.setNombreSucursal(domain.getNombreSucursal());
        entity.setCiudadSucursal(domain.getCiudadSucursal());
        entity.setFranquiciaId(domain.getFranquiciaId());
        return entity;
    }

    public Sucursal toDomain(SucursalEntity entity) {
        if (entity == null) {
            return null;
        }
        Sucursal domain = new Sucursal();
        domain.setIdSucursal(entity.getIdSucursal());
        domain.setNombreSucursal(entity.getNombreSucursal());
        domain.setCiudadSucursal(entity.getCiudadSucursal());
        domain.setFranquiciaId(entity.getFranquiciaId());
        return domain;
    }
}
