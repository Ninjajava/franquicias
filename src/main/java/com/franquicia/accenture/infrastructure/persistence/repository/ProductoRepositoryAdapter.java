package com.franquicia.accenture.infrastructure.persistence.repository;

import com.franquicia.accenture.domain.model.Producto;
import com.franquicia.accenture.domain.port.output.ProductoRepositoryPort;
import com.franquicia.accenture.infrastructure.persistence.entity.ProductoEntity;
import com.franquicia.accenture.infrastructure.persistence.mapper.ProductoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {

    private final ProductoR2dbcRepository productoR2dbcRepository;
    private final ProductoEntityMapper mapper;

    @Override
    public Mono<Producto> save(Producto producto) {
        ProductoEntity entity = mapper.toEntity(producto);
        return productoR2dbcRepository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Producto> findById(Long id) {
        return productoR2dbcRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return productoR2dbcRepository.deleteById(id);
    }

    @Override
    public Flux<Producto> findBySucursalId(Long sucursalId) {
        return productoR2dbcRepository.findBySucursalId(sucursalId)
                .map(mapper::toDomain);
    }
}
