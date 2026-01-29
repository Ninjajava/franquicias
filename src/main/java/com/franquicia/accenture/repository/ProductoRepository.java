package com.franquicia.accenture.repository;

import com.franquicia.accenture.model.Producto;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ProductoRepository
        extends R2dbcRepository<Producto, Long> {

    Flux<Producto> findBySucursalId(Long sucursalId);



}

