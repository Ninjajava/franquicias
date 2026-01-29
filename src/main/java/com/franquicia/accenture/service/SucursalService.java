package com.franquicia.accenture.service;

import com.franquicia.accenture.dto.CrearSucursalRequest;
import com.franquicia.accenture.dto.ProductoMaxStockResponse;
import com.franquicia.accenture.model.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalService {

    Mono<Sucursal> crearSucursal(Long franquiciaId, CrearSucursalRequest request);

}
