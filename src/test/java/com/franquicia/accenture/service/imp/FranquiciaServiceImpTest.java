package com.franquicia.accenture.service.imp;

import com.franquicia.accenture.application.dto.CrearFranquiciaRequest;
import com.franquicia.accenture.application.dto.ProductoMaxStockResponse;
import com.franquicia.accenture.application.dto.ProductoRequest;
import com.franquicia.accenture.application.dto.SucursalRequest;
import com.franquicia.accenture.application.service.FranquiciaService;
import com.franquicia.accenture.domain.model.Franquicia;
import com.franquicia.accenture.domain.model.Producto;
import com.franquicia.accenture.domain.model.Sucursal;
import com.franquicia.accenture.domain.port.output.FranquiciaRepositoryPort;
import com.franquicia.accenture.domain.port.output.ProductoRepositoryPort;
import com.franquicia.accenture.domain.port.output.SucursalRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranquiciaServiceImpTest {

    @Mock
    private FranquiciaRepositoryPort franquiciaRepo;

    @Mock
    private SucursalRepositoryPort sucursalRepo;

    @Mock
    private ProductoRepositoryPort productoRepo;

    @InjectMocks
    private FranquiciaService service;

    private Franquicia franquicia;
    private Sucursal sucursal;
    private Producto producto;
    private CrearFranquiciaRequest request;

    @BeforeEach
    void setUp() {
        franquicia = new Franquicia();
        franquicia.setFranquiciaId(1L);
        franquicia.setNombre("Franquicia Test");
        franquicia.setCiudad("Ciudad Test");

        sucursal = new Sucursal();
        sucursal.setIdSucursal(1L);
        sucursal.setNombreSucursal("Sucursal Test");
        sucursal.setCiudadSucursal("Ciudad Sucursal");
        sucursal.setFranquiciaId(1L);

        producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Producto Test");
        producto.setPrecio(BigDecimal.valueOf(100.0));
        producto.setCantidadProducto(50);
        producto.setSucursalId(1L);

        ProductoRequest productoRequest = new ProductoRequest();
        productoRequest.setNombre("Producto Test");
        productoRequest.setPrecio(BigDecimal.valueOf(100.0));

        SucursalRequest sucursalRequest = new SucursalRequest();
        sucursalRequest.setNombre("Sucursal Test");
        sucursalRequest.setCiudad("Ciudad Sucursal");
        sucursalRequest.setProductos(Arrays.asList(productoRequest));

        request = new CrearFranquiciaRequest();
        request.setNombre("Franquicia Test");
        request.setCiudad("Ciudad Test");
        request.setSucursales(Arrays.asList(sucursalRequest));
    }

    @Test
    void crearFranquicia_Success() {
        // Arrange
        when(franquiciaRepo.save(any(Franquicia.class))).thenReturn(Mono.just(franquicia));
        when(sucursalRepo.save(any(Sucursal.class))).thenReturn(Mono.just(sucursal));
        when(productoRepo.save(any(Producto.class))).thenReturn(Mono.just(producto));

        // Act & Assert
        StepVerifier.create(service.crearFranquicia(request))
                .expectNext(franquicia)
                .verifyComplete();

        verify(franquiciaRepo, times(1)).save(any(Franquicia.class));
        verify(sucursalRepo, times(1)).save(any(Sucursal.class));
        verify(productoRepo, times(1)).save(any(Producto.class));
    }

    @Test
    void crearFranquicia_WithMultipleSucursales_Success() {
        // Arrange
        ProductoRequest productoRequest1 = new ProductoRequest();
        productoRequest1.setNombre("Producto 1");
        productoRequest1.setPrecio(BigDecimal.valueOf(50.0));

        ProductoRequest productoRequest2 = new ProductoRequest();
        productoRequest2.setNombre("Producto 2");
        productoRequest2.setPrecio(BigDecimal.valueOf(75.0));

        SucursalRequest sucursalRequest1 = new SucursalRequest();
        sucursalRequest1.setNombre("Sucursal 1");
        sucursalRequest1.setCiudad("Ciudad 1");
        sucursalRequest1.setProductos(Arrays.asList(productoRequest1));

        SucursalRequest sucursalRequest2 = new SucursalRequest();
        sucursalRequest2.setNombre("Sucursal 2");
        sucursalRequest2.setCiudad("Ciudad 2");
        sucursalRequest2.setProductos(Arrays.asList(productoRequest2));

        request.setSucursales(Arrays.asList(sucursalRequest1, sucursalRequest2));

        Sucursal sucursal2 = new Sucursal();
        sucursal2.setIdSucursal(2L);
        sucursal2.setNombreSucursal("Sucursal 2");
        sucursal2.setCiudadSucursal("Ciudad 2");
        sucursal2.setFranquiciaId(1L);

        when(franquiciaRepo.save(any(Franquicia.class))).thenReturn(Mono.just(franquicia));
        when(sucursalRepo.save(any(Sucursal.class)))
                .thenReturn(Mono.just(sucursal))
                .thenReturn(Mono.just(sucursal2));
        when(productoRepo.save(any(Producto.class))).thenReturn(Mono.just(producto));

        // Act & Assert
        StepVerifier.create(service.crearFranquicia(request))
                .expectNext(franquicia)
                .verifyComplete();

        verify(franquiciaRepo, times(1)).save(any(Franquicia.class));
        verify(sucursalRepo, times(2)).save(any(Sucursal.class));
        verify(productoRepo, times(2)).save(any(Producto.class));
    }

    @Test
    void maxStockProducto_Success() {
        // Arrange
        Producto producto1 = new Producto();
        producto1.setIdProducto(1L);
        producto1.setNombreProducto("Producto 1");
        producto1.setCantidadProducto(30);
        producto1.setSucursalId(1L);

        Producto producto2 = new Producto();
        producto2.setIdProducto(2L);
        producto2.setNombreProducto("Producto 2");
        producto2.setCantidadProducto(50);
        producto2.setSucursalId(1L);

        Producto producto3 = new Producto();
        producto3.setIdProducto(3L);
        producto3.setNombreProducto("Producto 3");
        producto3.setCantidadProducto(40);
        producto3.setSucursalId(2L);

        Sucursal sucursal1 = new Sucursal();
        sucursal1.setIdSucursal(1L);
        sucursal1.setNombreSucursal("Sucursal 1");
        sucursal1.setFranquiciaId(1L);

        Sucursal sucursal2 = new Sucursal();
        sucursal2.setIdSucursal(2L);
        sucursal2.setNombreSucursal("Sucursal 2");
        sucursal2.setFranquiciaId(1L);

        when(sucursalRepo.findByFranquiciaId(1L))
                .thenReturn(Flux.just(sucursal1, sucursal2));
        when(productoRepo.findBySucursalId(1L))
                .thenReturn(Flux.just(producto1, producto2));
        when(productoRepo.findBySucursalId(2L))
                .thenReturn(Flux.just(producto3));

        // Act & Assert
        StepVerifier.create(service.maxStockProducto(1L))
                .expectNextMatches(response ->
                        response.getStock() == 50 &&
                        response.getProducto().equals("Producto 2") &&
                        response.getSucursal().equals("Sucursal 1")
                )
                .verifyComplete();

        verify(sucursalRepo, times(1)).findByFranquiciaId(1L);
        verify(productoRepo, times(1)).findBySucursalId(1L);
        verify(productoRepo, times(1)).findBySucursalId(2L);
    }

    @Test
    void maxStockProducto_NoSucursales_ReturnsEmpty() {
        // Arrange
        when(sucursalRepo.findByFranquiciaId(anyLong()))
                .thenReturn(Flux.empty());

        // Act & Assert
        StepVerifier.create(service.maxStockProducto(999L))
                .expectNextCount(0)
                .verifyComplete();

        verify(sucursalRepo, times(1)).findByFranquiciaId(999L);
        verify(productoRepo, never()).findBySucursalId(anyLong());
    }

    @Test
    void maxStockProducto_NoProductos_ReturnsEmpty() {
        // Arrange
        when(sucursalRepo.findByFranquiciaId(anyLong()))
                .thenReturn(Flux.just(sucursal));
        when(productoRepo.findBySucursalId(anyLong()))
                .thenReturn(Flux.empty());

        // Act & Assert
        StepVerifier.create(service.maxStockProducto(1L))
                .expectNextCount(0)
                .verifyComplete();

        verify(sucursalRepo, times(1)).findByFranquiciaId(1L);
        verify(productoRepo, times(1)).findBySucursalId(1L);
    }
}
