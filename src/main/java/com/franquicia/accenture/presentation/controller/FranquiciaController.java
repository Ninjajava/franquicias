package com.franquicia.accenture.presentation.controller;

import com.franquicia.accenture.application.dto.CrearFranquiciaRequest;
import com.franquicia.accenture.application.dto.ProductoMaxStockResponse;
import com.franquicia.accenture.application.service.FranquiciaService;
import com.franquicia.accenture.domain.model.Franquicia;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class FranquiciaController {

    private final FranquiciaService service;

    @PostMapping("/crear")
    public Mono<Franquicia> CreateFranquicia(@RequestBody CrearFranquiciaRequest franquicia){
       return  service.crearFranquicia(franquicia);
    }

    @GetMapping("/franquicia/{franquiciaId}/productos-max-stock")
    public Mono<ProductoMaxStockResponse> listaDeFranquiciasStockMax(@PathVariable Long franquiciaId ){
        return service.maxStockProducto(franquiciaId);
    }
}
