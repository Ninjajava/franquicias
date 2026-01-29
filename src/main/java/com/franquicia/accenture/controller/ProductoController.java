package com.franquicia.accenture.controller;

import com.franquicia.accenture.dto.ActualizarStockRequest;
import com.franquicia.accenture.dto.CrearProductoRequest;
import com.franquicia.accenture.model.Producto;
import com.franquicia.accenture.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;
    @PostMapping("/sucursal/{sucursalId}/producto")
    public Mono<Producto>crearProducto(@PathVariable Long sucursalId, @RequestBody CrearProductoRequest request){

        return  service.crearProducto(sucursalId,request);
    }

    @DeleteMapping("/producto/{idProducto}")
    Mono<Void>eliminarProducto(@PathVariable Long idProducto){
        return service.eliminarProducto(idProducto);
    }

    @PutMapping("/producto/{idProducto}/stock")
    Mono<Producto>modificarStock(@PathVariable Long idProducto, @RequestBody ActualizarStockRequest request){
      return service.actualizarStock(idProducto,request);
    }
}
