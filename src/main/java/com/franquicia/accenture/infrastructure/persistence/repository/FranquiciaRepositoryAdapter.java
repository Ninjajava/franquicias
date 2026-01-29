package com.franquicia.accenture.infrastructure.persistence.repository;

import com.franquicia.accenture.domain.model.Franquicia;
import com.franquicia.accenture.domain.port.output.FranquiciaRepositoryPort;
import com.franquicia.accenture.infrastructure.persistence.entity.FranquiciaEntity;
import com.franquicia.accenture.infrastructure.persistence.mapper.FranquiciaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class FranquiciaRepositoryAdapter implements FranquiciaRepositoryPort {

    private final FranquiciaR2dbcRepository franquiciaR2dbcRepository;
    private final FranquiciaEntityMapper mapper;

    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        FranquiciaEntity entity = mapper.toEntity(franquicia);
        return franquiciaR2dbcRepository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franquicia> findById(Long id) {
        return franquiciaR2dbcRepository.findById(id)
                .map(mapper::toDomain);
    }
}
