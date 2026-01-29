package com.franquicia.accenture.domain.port.output;

import com.franquicia.accenture.domain.model.Franquicia;
import reactor.core.publisher.Mono;

public interface FranquiciaRepositoryPort {
    Mono<Franquicia> save(Franquicia franquicia);
    Mono<Franquicia> findById(Long id);
}
