package com.franquicia.accenture.domain.usecase;

import com.franquicia.accenture.domain.model.Producto;
import reactor.core.publisher.Mono;

public interface ProductoUseCase {
    Mono<Producto> crearProducto(Producto producto);
    Mono<Void> eliminarProducto(Long idProducto);
    Mono<Producto> actualizarStock(Long idProducto, Integer cantidad);
}
