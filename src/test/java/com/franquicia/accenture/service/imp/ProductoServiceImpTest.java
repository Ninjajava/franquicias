package com.franquicia.accenture.service.imp;

import com.franquicia.accenture.dto.ActualizarStockRequest;
import com.franquicia.accenture.dto.CrearProductoRequest;
import com.franquicia.accenture.model.Producto;
import com.franquicia.accenture.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImpTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImp service;

    private Producto producto;
    private CrearProductoRequest crearRequest;
    private ActualizarStockRequest actualizarRequest;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Producto Test");
        producto.setPrecio(BigDecimal.valueOf(100.0));
        producto.setCantidadProducto(10);
        producto.setSucursalId(1L);

        crearRequest = new CrearProductoRequest();
        crearRequest.setNombre("Nuevo Producto");
        crearRequest.setPrecio(BigDecimal.valueOf(150.0));
        crearRequest.setCantidad(20);

        actualizarRequest = new ActualizarStockRequest();
        actualizarRequest.setCantidad(50);
    }

    @Test
    void crearProducto_Success() {
        // Arrange
        Producto productoGuardado = new Producto();
        productoGuardado.setIdProducto(1L);
        productoGuardado.setNombreProducto(crearRequest.getNombre());
        productoGuardado.setPrecio(crearRequest.getPrecio());
        productoGuardado.setCantidadProducto(crearRequest.getCantidad());
        productoGuardado.setSucursalId(1L);

        when(productoRepository.save(any(Producto.class)))
                .thenReturn(Mono.just(productoGuardado));

        // Act & Assert
        StepVerifier.create(service.crearProducto(1L, crearRequest))
                .expectNextMatches(savedProducto ->
                        savedProducto.getNombreProducto().equals("Nuevo Producto") &&
                        savedProducto.getPrecio().equals(BigDecimal.valueOf(150.0)) &&
                        savedProducto.getCantidadProducto() == 20 &&
                        savedProducto.getSucursalId() == 1L
                )
                .verifyComplete();

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void crearProducto_VerifyFieldsSetCorrectly() {
        // Arrange
        when(productoRepository.save(any(Producto.class)))
                .thenAnswer(invocation -> {
                    Producto p = invocation.getArgument(0);
                    p.setIdProducto(1L);
                    return Mono.just(p);
                });

        // Act
        StepVerifier.create(service.crearProducto(2L, crearRequest))
                .expectNextCount(1)
                .verifyComplete();

        // Assert
        verify(productoRepository, times(1)).save(argThat(p ->
                p.getNombreProducto().equals("Nuevo Producto") &&
                p.getPrecio().equals(BigDecimal.valueOf(150.0)) &&
                p.getCantidadProducto() == 20 &&
                p.getSucursalId() == 2L
        ));
    }

    @Test
    void eliminarProducto_Success() {
        // Arrange
        when(productoRepository.deleteById(1L))
                .thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.eliminarProducto(1L))
                .verifyComplete();

        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void actualizarStock_Success() {
        // Arrange
        Producto productoActualizado = new Producto();
        productoActualizado.setIdProducto(1L);
        productoActualizado.setNombreProducto("Producto Test");
        productoActualizado.setPrecio(BigDecimal.valueOf(100.0));
        productoActualizado.setCantidadProducto(50);
        productoActualizado.setSucursalId(1L);

        when(productoRepository.findById(1L))
                .thenReturn(Mono.just(producto));
        when(productoRepository.save(any(Producto.class)))
                .thenReturn(Mono.just(productoActualizado));

        // Act & Assert
        StepVerifier.create(service.actualizarStock(1L, actualizarRequest))
                .expectNextMatches(updatedProducto ->
                        updatedProducto.getCantidadProducto() == 50 &&
                        updatedProducto.getIdProducto() == 1L
                )
                .verifyComplete();

        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void actualizarStock_ProductoNoExiste_ThrowsException() {
        // Arrange
        when(productoRepository.findById(999L))
                .thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.actualizarStock(999L, actualizarRequest))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Producto no existe")
                )
                .verify();

        verify(productoRepository, times(1)).findById(999L);
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void actualizarStock_VerifyStockUpdated() {
        // Arrange
        actualizarRequest.setCantidad(75);

        when(productoRepository.findById(1L))
                .thenReturn(Mono.just(producto));
        when(productoRepository.save(any(Producto.class)))
                .thenAnswer(invocation -> {
                    Producto p = invocation.getArgument(0);
                    return Mono.just(p);
                });

        // Act
        StepVerifier.create(service.actualizarStock(1L, actualizarRequest))
                .expectNextMatches(p -> p.getCantidadProducto() == 75)
                .verifyComplete();

        // Assert
        verify(productoRepository, times(1)).save(argThat(p ->
                p.getCantidadProducto() == 75
        ));
    }
}
