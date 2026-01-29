package com.franquicia.accenture.controller;

import com.franquicia.accenture.dto.CrearSucursalRequest;
import com.franquicia.accenture.model.Sucursal;
import com.franquicia.accenture.service.SucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class SucursalController {

    private final SucursalService service;

    @PostMapping("/franquicia/{franquiciaId}/sucursal")
    public Mono<Sucursal>crearSucursal(@PathVariable Long franquiciaId, @RequestBody CrearSucursalRequest request){

        return service.crearSucursal(franquiciaId, request);
    }
}
