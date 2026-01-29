package com.franquicia.accenture.service.imp;

import com.franquicia.accenture.dto.CrearFranquiciaRequest;
import com.franquicia.accenture.dto.ProductoMaxStockResponse;
import com.franquicia.accenture.model.Franquicia;
import com.franquicia.accenture.model.Producto;
import com.franquicia.accenture.model.Sucursal;
import com.franquicia.accenture.repository.FranquiciaRepository;
import com.franquicia.accenture.repository.ProductoRepository;
import com.franquicia.accenture.repository.SucursalRepository;
import com.franquicia.accenture.service.FranquiciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class FranquiciaServiceImp implements FranquiciaService {

    private final FranquiciaRepository franquiciaRepo;
    private final SucursalRepository sucursalRepo;
    private final ProductoRepository productoRepo;

    @Override
    public Mono<ProductoMaxStockResponse> maxStockProducto(Long franquiciaId) {
        return sucursalRepo.findByFranquiciaId(franquiciaId)
                .flatMap(sucursal ->
                        productoRepo.findBySucursalId(sucursal.getIdSucursal())
                                .map(producto ->
                                        new ProductoMaxStockResponse(
                                                sucursal.getNombreSucursal(),
                                                producto.getNombreProducto(),
                                                producto.getCantidadProducto()
                                        )
                                )
                )
                .sort((a, b) -> b.getStock() - a.getStock()) // orden descendente
                .next();
    }

    @Transactional
    @Override
    public Mono<Franquicia> crearFranquicia(CrearFranquiciaRequest request) {

        Franquicia franquicia = new Franquicia();
        franquicia.setNombre(request.getNombre());
        franquicia.setCiudad(request.getCiudad());

        return franquiciaRepo.save(franquicia)
                .flatMap(savedFranquicia ->

                        Flux.fromIterable(request.getSucursales())
                                .flatMap(sucursalReq -> {

                                    Sucursal sucursal = new Sucursal();
                                    sucursal.setNombreSucursal(sucursalReq.getNombre());
                                    sucursal.setCiudadSucursal(sucursalReq.getCiudad());
                                    sucursal.setFranquiciaId(savedFranquicia.getFranquiciaId());

                                    return sucursalRepo.save(sucursal)
                                            .flatMap(savedSucursal ->

                                                    Flux.fromIterable(sucursalReq.getProductos())
                                                            .flatMap(prodReq -> {

                                                                Producto producto = new Producto();
                                                                producto.setNombreProducto(prodReq.getNombre());
                                                                producto.setPrecio(prodReq.getPrecio());
                                                                producto.setCantidadProducto(1);
                                                                producto.setSucursalId(savedSucursal.getIdSucursal());

                                                                return productoRepo.save(producto);
                                                            })
                                                            .then() // termina productos
                                            );
                                })
                                .then(Mono.just(savedFranquicia)) // 👈 retornas la franquicia
                );
    }

}



