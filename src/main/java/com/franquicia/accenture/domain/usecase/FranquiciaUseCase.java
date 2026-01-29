package com.franquicia.accenture.domain.usecase;

import com.franquicia.accenture.domain.model.Franquicia;
import reactor.core.publisher.Mono;

public interface FranquiciaUseCase {
    Mono<Franquicia> crearFranquicia(Franquicia franquicia);
}
