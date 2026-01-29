package com.franquicia.accenture.domain.usecase;

import com.franquicia.accenture.domain.model.Sucursal;
import reactor.core.publisher.Mono;

public interface SucursalUseCase {
    Mono<Sucursal> crearSucursal(Sucursal sucursal);
}
