package com.franquicia.accenture.application.service;

import com.franquicia.accenture.application.dto.CrearSucursalRequest;
import com.franquicia.accenture.domain.model.Sucursal;
import com.franquicia.accenture.domain.port.output.SucursalRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SucursalService {

    private final SucursalRepositoryPort sucursalRepositoryPort;

    public Mono<Sucursal> crearSucursal(Long franquiciaId, CrearSucursalRequest request) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal(request.getNombre());
        sucursal.setCiudadSucursal(request.getCiudad());
        sucursal.setFranquiciaId(franquiciaId);

        return sucursalRepositoryPort.save(sucursal);
    }
}
