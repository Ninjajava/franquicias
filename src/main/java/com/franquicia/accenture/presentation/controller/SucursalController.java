package com.franquicia.accenture.presentation.controller;

import com.franquicia.accenture.application.dto.CrearSucursalRequest;
import com.franquicia.accenture.application.service.SucursalService;
import com.franquicia.accenture.domain.model.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class SucursalController {

    private final SucursalService service;

    @PostMapping("/franquicia/{franquiciaId}/sucursal")
    public Mono<Sucursal> crearSucursal(@PathVariable Long franquiciaId, @RequestBody CrearSucursalRequest request){
        return service.crearSucursal(franquiciaId, request);
    }
}
