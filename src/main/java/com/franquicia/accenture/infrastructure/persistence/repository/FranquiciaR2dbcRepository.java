package com.franquicia.accenture.infrastructure.persistence.repository;

import com.franquicia.accenture.infrastructure.persistence.entity.FranquiciaEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranquiciaR2dbcRepository extends R2dbcRepository<FranquiciaEntity, Long> {
}
