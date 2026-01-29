package com.franquicia.accenture.infrastructure.persistence.repository;

import com.franquicia.accenture.domain.model.Sucursal;
import com.franquicia.accenture.domain.port.output.SucursalRepositoryPort;
import com.franquicia.accenture.infrastructure.persistence.entity.SucursalEntity;
import com.franquicia.accenture.infrastructure.persistence.mapper.SucursalEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class SucursalRepositoryAdapter implements SucursalRepositoryPort {

    private final SucursalR2dbcRepository sucursalR2dbcRepository;
    private final SucursalEntityMapper mapper;

    @Override
    public Mono<Sucursal> save(Sucursal sucursal) {
        SucursalEntity entity = mapper.toEntity(sucursal);
        return sucursalR2dbcRepository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Sucursal> findByFranquiciaId(Long franquiciaId) {
        return sucursalR2dbcRepository.findByFranquiciaId(franquiciaId)
                .map(mapper::toDomain);
    }
}
