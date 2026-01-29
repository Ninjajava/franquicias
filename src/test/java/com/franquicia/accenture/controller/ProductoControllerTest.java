package com.franquicia.accenture.controller;

import com.franquicia.accenture.dto.ActualizarStockRequest;
import com.franquicia.accenture.dto.CrearProductoRequest;
import com.franquicia.accenture.model.Producto;
import com.franquicia.accenture.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private ProductoService service;

    @InjectMocks
    private ProductoController controller;

    private Producto producto;
    private CrearProductoRequest crearRequest;
    private ActualizarStockRequest actualizarRequest;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(controller).build();

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

        when(service.crearProducto(anyLong(), any(CrearProductoRequest.class)))
                .thenReturn(Mono.just(productoGuardado));

        // Act & Assert
        webTestClient.post()
                .uri("/sucursal/1/producto")
                .bodyValue(crearRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.idProducto").exists()
                .jsonPath("$.nombreProducto").isEqualTo("Nuevo Producto")
                .jsonPath("$.precio").isEqualTo(150.0)
                .jsonPath("$.cantidadProducto").isEqualTo(20)
                .jsonPath("$.sucursalId").isEqualTo(1);
    }

    @Test
    void crearProducto_VerifyServiceCalledWithCorrectParameters() {
        // Arrange
        when(service.crearProducto(anyLong(), any(CrearProductoRequest.class)))
                .thenReturn(Mono.just(producto));

        // Act
        webTestClient.post()
                .uri("/sucursal/5/producto")
                .bodyValue(crearRequest)
                .exchange()
                .expectStatus().isOk();

        // Assert
        verify(service, times(1)).crearProducto(eq(5L), any(CrearProductoRequest.class));
    }

    @Test
    void eliminarProducto_Success() {
        // Arrange
        when(service.eliminarProducto(anyLong()))
                .thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.delete()
                .uri("/producto/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        verify(service, times(1)).eliminarProducto(1L);
    }

    @Test
    void eliminarProducto_DifferentId() {
        // Arrange
        when(service.eliminarProducto(anyLong()))
                .thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.delete()
                .uri("/producto/999")
                .exchange()
                .expectStatus().isOk();

        verify(service, times(1)).eliminarProducto(999L);
    }

    @Test
    void modificarStock_Success() {
        // Arrange
        Producto productoActualizado = new Producto();
        productoActualizado.setIdProducto(1L);
        productoActualizado.setNombreProducto("Producto Test");
        productoActualizado.setPrecio(BigDecimal.valueOf(100.0));
        productoActualizado.setCantidadProducto(50);
        productoActualizado.setSucursalId(1L);

        when(service.actualizarStock(anyLong(), any(ActualizarStockRequest.class)))
                .thenReturn(Mono.just(productoActualizado));

        // Act & Assert
        webTestClient.put()
                .uri("/producto/1/stock")
                .bodyValue(actualizarRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.idProducto").isEqualTo(1)
                .jsonPath("$.cantidadProducto").isEqualTo(50);
    }

    @Test
    void modificarStock_VerifyServiceCalledWithCorrectParameters() {
        // Arrange
        when(service.actualizarStock(anyLong(), any(ActualizarStockRequest.class)))
                .thenReturn(Mono.just(producto));

        // Act
        webTestClient.put()
                .uri("/producto/2/stock")
                .bodyValue(actualizarRequest)
                .exchange()
                .expectStatus().isOk();

        // Assert
        verify(service, times(1)).actualizarStock(eq(2L), any(ActualizarStockRequest.class));
    }
}
