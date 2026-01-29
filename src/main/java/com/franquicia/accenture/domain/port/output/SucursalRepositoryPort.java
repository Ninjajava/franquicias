package com.franquicia.accenture.domain.port.output;

import com.franquicia.accenture.domain.model.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalRepositoryPort {
    Mono<Sucursal> save(Sucursal sucursal);
    Flux<Sucursal> findByFranquiciaId(Long franquiciaId);
}
