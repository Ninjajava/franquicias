package com.franquicia.accenture.domain.port.output;

import com.franquicia.accenture.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepositoryPort {
    Mono<Producto> save(Producto producto);
    Mono<Producto> findById(Long id);
    Mono<Void> deleteById(Long id);
    Flux<Producto> findBySucursalId(Long sucursalId);
}
