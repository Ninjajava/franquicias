package com.franquicia.accenture.service.imp;

import com.franquicia.accenture.dto.ActualizarStockRequest;
import com.franquicia.accenture.dto.CrearProductoRequest;
import com.franquicia.accenture.model.Producto;
import com.franquicia.accenture.repository.ProductoRepository;
import com.franquicia.accenture.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoServiceImp implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public Mono<Producto> actualizarStock(Long idProducto, ActualizarStockRequest request) {
        return productoRepository.findById(idProducto)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no existe")))
                .flatMap(producto -> {
                    producto.setCantidadProducto(request.getCantidad());
                    return productoRepository.save(producto);
                });
    }

    @Override
    public Mono<Void> eliminarProducto(Long idProductoo) {
        return productoRepository.deleteById(idProductoo);
    }

    @Override
    public Mono<Producto> crearProducto(Long sucursalId, CrearProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombreProducto(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setCantidadProducto(request.getCantidad());
        producto.setSucursalId(sucursalId);

        return productoRepository.save(producto);

    }
}
