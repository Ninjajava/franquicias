package com.franquicia.accenture.controller;

import com.franquicia.accenture.application.dto.CrearSucursalRequest;
import com.franquicia.accenture.application.service.SucursalService;
import com.franquicia.accenture.domain.model.Sucursal;
import com.franquicia.accenture.presentation.controller.SucursalController;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SucursalControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private SucursalService service;

    @InjectMocks
    private SucursalController controller;

    private Sucursal sucursal;
    private CrearSucursalRequest request;

    @BeforeEach
    void setUp() {
        controller = new SucursalController(service);
        webTestClient = WebTestClient.bindToController(controller).build();

        sucursal = new Sucursal();
        sucursal.setIdSucursal(1L);
        sucursal.setNombreSucursal("Sucursal Test");
        sucursal.setCiudadSucursal("Ciudad Test");
        sucursal.setFranquiciaId(1L);

        request = new CrearSucursalRequest();
        request.setNombre("Sucursal Test");
        request.setCiudad("Ciudad Test");
    }

    @Test
    void crearSucursal_Success() {
        // Arrange
        when(service.crearSucursal(anyLong(), any(CrearSucursalRequest.class)))
                .thenReturn(Mono.just(sucursal));

        // Act & Assert
        webTestClient.post()
                .uri("/franquicia/1/sucursal")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.idSucursal").exists()
                .jsonPath("$.nombreSucursal").isEqualTo("Sucursal Test")
                .jsonPath("$.ciudadSucursal").isEqualTo("Ciudad Test")
                .jsonPath("$.franquiciaId").isEqualTo(1);
    }

    @Test
    void crearSucursal_VerifyServiceCalledWithCorrectParameters() {
        // Arrange
        when(service.crearSucursal(anyLong(), any(CrearSucursalRequest.class)))
                .thenReturn(Mono.just(sucursal));

        // Act
        webTestClient.post()
                .uri("/franquicia/5/sucursal")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();

        // Assert
        verify(service, times(1)).crearSucursal(eq(5L), any(CrearSucursalRequest.class));
    }

    @Test
    void crearSucursal_DifferentFranquiciaId() {
        // Arrange
        Sucursal sucursal2 = new Sucursal();
        sucursal2.setIdSucursal(2L);
        sucursal2.setNombreSucursal("Sucursal Test");
        sucursal2.setCiudadSucursal("Ciudad Test");
        sucursal2.setFranquiciaId(10L);

        when(service.crearSucursal(anyLong(), any(CrearSucursalRequest.class)))
                .thenReturn(Mono.just(sucursal2));

        // Act & Assert
        webTestClient.post()
                .uri("/franquicia/10/sucursal")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.franquiciaId").isEqualTo(10);
    }

    @Test
    void crearSucursal_VerifyRequestBodyMapping() {
        // Arrange
        CrearSucursalRequest newRequest = new CrearSucursalRequest();
        newRequest.setNombre("Nueva Sucursal");
        newRequest.setCiudad("Nueva Ciudad");

        Sucursal nuevaSucursal = new Sucursal();
        nuevaSucursal.setIdSucursal(1L);
        nuevaSucursal.setNombreSucursal("Nueva Sucursal");
        nuevaSucursal.setCiudadSucursal("Nueva Ciudad");
        nuevaSucursal.setFranquiciaId(1L);

        when(service.crearSucursal(anyLong(), any(CrearSucursalRequest.class)))
                .thenReturn(Mono.just(nuevaSucursal));

        // Act & Assert
        webTestClient.post()
                .uri("/franquicia/1/sucursal")
                .bodyValue(newRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombreSucursal").isEqualTo("Nueva Sucursal")
                .jsonPath("$.ciudadSucursal").isEqualTo("Nueva Ciudad");
    }
}
