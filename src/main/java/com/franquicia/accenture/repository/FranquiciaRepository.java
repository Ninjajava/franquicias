package com.franquicia.accenture.repository;

import com.franquicia.accenture.model.Franquicia;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface FranquiciaRepository  extends R2dbcRepository<Franquicia, Long> {
}
