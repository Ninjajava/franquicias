package com.franquicia.accenture.service;

import com.franquicia.accenture.dto.ActualizarStockRequest;
import com.franquicia.accenture.dto.CrearProductoRequest;
import com.franquicia.accenture.model.Producto;
import reactor.core.publisher.Mono;

public interface ProductoService {

    Mono<Producto> crearProducto(Long sucursalId, CrearProductoRequest request);

    Mono<Void>eliminarProducto(Long idProductoo);

    Mono<Producto>actualizarStock(Long idProducto, ActualizarStockRequest request);
}
