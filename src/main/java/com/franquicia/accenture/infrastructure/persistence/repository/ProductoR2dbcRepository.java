package com.franquicia.accenture.infrastructure.persistence.repository;

import com.franquicia.accenture.infrastructure.persistence.entity.ProductoEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductoR2dbcRepository extends R2dbcRepository<ProductoEntity, Long> {
    Flux<ProductoEntity> findBySucursalId(Long sucursalId);
}
