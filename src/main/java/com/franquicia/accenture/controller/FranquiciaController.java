package com.franquicia.accenture.controller;

import com.franquicia.accenture.dto.CrearFranquiciaRequest;
import com.franquicia.accenture.dto.ProductoMaxStockResponse;
import com.franquicia.accenture.model.Franquicia;
import com.franquicia.accenture.service.FranquiciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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
    public Mono<ProductoMaxStockResponse>listaDeFranquiciasStockMax(@PathVariable Long franquiciaId ){
        return service.maxStockProducto(franquiciaId);
    }
}
