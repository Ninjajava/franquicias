package com.franquicia.accenture.service.imp;

import com.franquicia.accenture.application.dto.CrearSucursalRequest;
import com.franquicia.accenture.application.service.SucursalService;
import com.franquicia.accenture.domain.model.Sucursal;
import com.franquicia.accenture.domain.port.output.SucursalRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SucursalServiceImpTest {

    @Mock
    private SucursalRepositoryPort sucursalRepository;

    @InjectMocks
    private SucursalService service;

    private Sucursal sucursal;
    private CrearSucursalRequest request;

    @BeforeEach
    void setUp() {
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
        Sucursal sucursalGuardada = new Sucursal();
        sucursalGuardada.setIdSucursal(1L);
        sucursalGuardada.setNombreSucursal(request.getNombre());
        sucursalGuardada.setCiudadSucursal(request.getCiudad());
        sucursalGuardada.setFranquiciaId(1L);

        when(sucursalRepository.save(any(Sucursal.class)))
                .thenReturn(Mono.just(sucursalGuardada));

        // Act & Assert
        StepVerifier.create(service.crearSucursal(1L, request))
                .expectNextMatches(savedSucursal ->
                        savedSucursal.getNombreSucursal().equals("Sucursal Test") &&
                        savedSucursal.getCiudadSucursal().equals("Ciudad Test") &&
                        savedSucursal.getFranquiciaId() == 1L
                )
                .verifyComplete();

        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }

    @Test
    void crearSucursal_VerifyFieldsSetCorrectly() {
        // Arrange
        request.setNombre("Nueva Sucursal");
        request.setCiudad("Nueva Ciudad");

        when(sucursalRepository.save(any(Sucursal.class)))
                .thenAnswer(invocation -> {
                    Sucursal s = invocation.getArgument(0);
                    s.setIdSucursal(2L);
                    return Mono.just(s);
                });

        // Act
        StepVerifier.create(service.crearSucursal(5L, request))
                .expectNextCount(1)
                .verifyComplete();

        // Assert
        verify(sucursalRepository, times(1)).save(argThat(s ->
                s.getNombreSucursal().equals("Nueva Sucursal") &&
                s.getCiudadSucursal().equals("Nueva Ciudad") &&
                s.getFranquiciaId() == 5L
        ));
    }

    @Test
    void crearSucursal_DifferentFranquiciaId() {
        // Arrange
        Sucursal sucursalGuardada = new Sucursal();
        sucursalGuardada.setIdSucursal(1L);
        sucursalGuardada.setNombreSucursal(request.getNombre());
        sucursalGuardada.setCiudadSucursal(request.getCiudad());
        sucursalGuardada.setFranquiciaId(10L);

        when(sucursalRepository.save(any(Sucursal.class)))
                .thenReturn(Mono.just(sucursalGuardada));

        // Act & Assert
        StepVerifier.create(service.crearSucursal(10L, request))
                .expectNextMatches(savedSucursal ->
                        savedSucursal.getFranquiciaId() == 10L
                )
                .verifyComplete();

        verify(sucursalRepository, times(1)).save(argThat(s ->
                s.getFranquiciaId() == 10L
        ));
    }
}
