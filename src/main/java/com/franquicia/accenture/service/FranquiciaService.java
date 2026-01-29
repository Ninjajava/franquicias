package com.franquicia.accenture.service;

import com.franquicia.accenture.dto.CrearFranquiciaRequest;
import com.franquicia.accenture.dto.ProductoMaxStockResponse;
import com.franquicia.accenture.model.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaService {

    Mono<Franquicia> crearFranquicia(CrearFranquiciaRequest franquicia);

    Mono<ProductoMaxStockResponse> maxStockProducto(Long franquiciaId);
}
