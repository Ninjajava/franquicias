package com.franquicia.accenture.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("FRANQUICIA")
@Data
public class Franquicia {

    @Id
    Long franquiciaId;
    String nombre;
    String ciudad;
}
