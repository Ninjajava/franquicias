package com.franquicia.accenture.infrastructure.persistence.mapper;

import com.franquicia.accenture.domain.model.Franquicia;
import com.franquicia.accenture.infrastructure.persistence.entity.FranquiciaEntity;
import org.springframework.stereotype.Component;

@Component
public class FranquiciaEntityMapper {

    public FranquiciaEntity toEntity(Franquicia domain) {
        if (domain == null) {
            return null;
        }
        FranquiciaEntity entity = new FranquiciaEntity();
        entity.setFranquiciaId(domain.getFranquiciaId());
        entity.setNombre(domain.getNombre());
        entity.setCiudad(domain.getCiudad());
        return entity;
    }

    public Franquicia toDomain(FranquiciaEntity entity) {
        if (entity == null) {
            return null;
        }
        Franquicia domain = new Franquicia();
        domain.setFranquiciaId(entity.getFranquiciaId());
        domain.setNombre(entity.getNombre());
        domain.setCiudad(entity.getCiudad());
        return domain;
    }
}
