package com.franquicia.accenture.application.service;

import com.franquicia.accenture.application.dto.ActualizarStockRequest;
import com.franquicia.accenture.application.dto.CrearProductoRequest;
import com.franquicia.accenture.domain.model.Producto;
import com.franquicia.accenture.domain.port.output.ProductoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepositoryPort productoRepositoryPort;

    public Mono<Producto> crearProducto(Long sucursalId, CrearProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombreProducto(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setCantidadProducto(request.getCantidad());
        producto.setSucursalId(sucursalId);

        return productoRepositoryPort.save(producto);
    }

    public Mono<Void> eliminarProducto(Long idProducto) {
        return productoRepositoryPort.deleteById(idProducto);
    }

    public Mono<Producto> actualizarStock(Long idProducto, ActualizarStockRequest request) {
        return productoRepositoryPort.findById(idProducto)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no existe")))
                .flatMap(producto -> {
                    producto.setCantidadProducto(request.getCantidad());
                    return productoRepositoryPort.save(producto);
                });
    }
}
