package com.franquicia.accenture.application.service;

import com.franquicia.accenture.application.dto.CrearFranquiciaRequest;
import com.franquicia.accenture.application.dto.ProductoMaxStockResponse;
import com.franquicia.accenture.domain.model.Franquicia;
import com.franquicia.accenture.domain.model.Producto;
import com.franquicia.accenture.domain.model.Sucursal;
import com.franquicia.accenture.domain.port.output.FranquiciaRepositoryPort;
import com.franquicia.accenture.domain.port.output.ProductoRepositoryPort;
import com.franquicia.accenture.domain.port.output.SucursalRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class FranquiciaService {

    private final FranquiciaRepositoryPort franquiciaRepositoryPort;
    private final SucursalRepositoryPort sucursalRepositoryPort;
    private final ProductoRepositoryPort productoRepositoryPort;

    @Transactional
    public Mono<Franquicia> crearFranquicia(CrearFranquiciaRequest request) {
        Franquicia franquicia = new Franquicia();
        franquicia.setNombre(request.getNombre());
        franquicia.setCiudad(request.getCiudad());

        return franquiciaRepositoryPort.save(franquicia)
                .flatMap(savedFranquicia ->
                        Flux.fromIterable(request.getSucursales())
                                .flatMap(sucursalReq -> {
                                    Sucursal sucursal = new Sucursal();
                                    sucursal.setNombreSucursal(sucursalReq.getNombre());
                                    sucursal.setCiudadSucursal(sucursalReq.getCiudad());
                                    sucursal.setFranquiciaId(savedFranquicia.getFranquiciaId());

                                    return sucursalRepositoryPort.save(sucursal)
                                            .flatMap(savedSucursal ->
                                                    Flux.fromIterable(sucursalReq.getProductos())
                                                            .flatMap(prodReq -> {
                                                                Producto producto = new Producto();
                                                                producto.setNombreProducto(prodReq.getNombre());
                                                                producto.setPrecio(prodReq.getPrecio());
                                                                producto.setCantidadProducto(1);
                                                                producto.setSucursalId(savedSucursal.getIdSucursal());
                                                                return productoRepositoryPort.save(producto);
                                                            })
                                                            .then()
                                            );
                                })
                                .then(Mono.just(savedFranquicia))
                );
    }

    public Mono<ProductoMaxStockResponse> maxStockProducto(Long franquiciaId) {
        return sucursalRepositoryPort.findByFranquiciaId(franquiciaId)
                .flatMap(sucursal ->
                        productoRepositoryPort.findBySucursalId(sucursal.getIdSucursal())
                                .map(producto ->
                                        new ProductoMaxStockResponse(
                                                sucursal.getNombreSucursal(),
                                                producto.getNombreProducto(),
                                                producto.getCantidadProducto()
                                        )
                                )
                )
                .sort((a, b) -> b.getStock() - a.getStock())
                .next();
    }
}
