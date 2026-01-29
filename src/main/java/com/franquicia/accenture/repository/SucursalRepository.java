package com.franquicia.accenture.repository;

import com.franquicia.accenture.model.Sucursal;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface SucursalRepository
        extends R2dbcRepository<Sucursal, Long> {

    Flux<Sucursal> findByFranquiciaId(Long franquiciaId);



}

