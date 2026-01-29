package com.franquicia.accenture.controller;

import com.franquicia.accenture.application.dto.CrearFranquiciaRequest;
import com.franquicia.accenture.application.dto.ProductoMaxStockResponse;
import com.franquicia.accenture.application.service.FranquiciaService;
import com.franquicia.accenture.domain.model.Franquicia;
import com.franquicia.accenture.presentation.controller.FranquiciaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranquiciaControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private FranquiciaService service;

    @InjectMocks
    private FranquiciaController controller;

    private Franquicia franquicia;
    private CrearFranquiciaRequest crearRequest;
    private ProductoMaxStockResponse maxStockResponse;

    @BeforeEach
    void setUp() {
        controller = new FranquiciaController(service);
        webTestClient = WebTestClient.bindToController(controller).build();

        franquicia = new Franquicia();
        franquicia.setFranquiciaId(1L);
        franquicia.setNombre("Franquicia Test");
        franquicia.setCiudad("Ciudad Test");

        crearRequest = new CrearFranquiciaRequest();
        crearRequest.setNombre("Franquicia Test");
        crearRequest.setCiudad("Ciudad Test");

        maxStockResponse = new ProductoMaxStockResponse(
                "Sucursal Test",
                "Producto Test",
                50
        );
    }

    @Test
    void createFranquicia_Success() {
        // Arrange
        when(service.crearFranquicia(any(CrearFranquiciaRequest.class)))
                .thenReturn(Mono.just(franquicia));

        // Act & Assert
        webTestClient.post()
                .uri("/crear")
                .bodyValue(crearRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.franquiciaId").exists()
                .jsonPath("$.nombre").isEqualTo("Franquicia Test")
                .jsonPath("$.ciudad").isEqualTo("Ciudad Test");
    }

    @Test
    void listaDeFranquiciasStockMax_Success() {
        // Arrange
        when(service.maxStockProducto(anyLong()))
                .thenReturn(Mono.just(maxStockResponse));

        // Act & Assert
        webTestClient.get()
                .uri("/franquicia/1/productos-max-stock")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.sucursal").isEqualTo("Sucursal Test")
                .jsonPath("$.producto").isEqualTo("Producto Test")
                .jsonPath("$.stock").isEqualTo(50);
    }

    @Test
    void listaDeFranquiciasStockMax_InvalidId() {
        // Arrange
        when(service.maxStockProducto(anyLong()))
                .thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.get()
                .uri("/franquicia/999/productos-max-stock")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }
}
