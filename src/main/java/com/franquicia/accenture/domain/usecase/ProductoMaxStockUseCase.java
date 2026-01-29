package com.franquicia.accenture.domain.usecase;

import com.franquicia.accenture.domain.model.Producto;
import reactor.core.publisher.Mono;

public interface ProductoMaxStockUseCase {
    Mono<Producto> obtenerProductoConMaximoStock(Long franquiciaId);
}
