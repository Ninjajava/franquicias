package com.franquicia.accenture.service.imp;

import com.franquicia.accenture.dto.CrearSucursalRequest;
import com.franquicia.accenture.dto.ProductoMaxStockResponse;
import com.franquicia.accenture.model.Sucursal;
import com.franquicia.accenture.repository.ProductoRepository;
import com.franquicia.accenture.repository.SucursalRepository;
import com.franquicia.accenture.service.SucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SucursalServiceImp implements SucursalService {

    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    @Override
    public Mono<Sucursal> crearSucursal(Long franquiciaId, CrearSucursalRequest request) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal(request.getNombre());
        sucursal.setCiudadSucursal(request.getCiudad());
        sucursal.setFranquiciaId(franquiciaId);

        return sucursalRepository.save(sucursal);
    }
}
