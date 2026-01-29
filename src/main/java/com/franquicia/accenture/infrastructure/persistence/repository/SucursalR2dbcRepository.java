package com.franquicia.accenture.infrastructure.persistence.repository;

import com.franquicia.accenture.infrastructure.persistence.entity.SucursalEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SucursalR2dbcRepository extends R2dbcRepository<SucursalEntity, Long> {
    Flux<SucursalEntity> findByFranquiciaId(Long franquiciaId);
}
