package com.franquicia.accenture.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("FRANQUICIA")
@Data
public class FranquiciaEntity {

    @Id
    Long franquiciaId;
    String nombre;
    String ciudad;
}
