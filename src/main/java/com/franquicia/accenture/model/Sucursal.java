package com.franquicia.accenture.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("SUCURSAL")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sucursal {

    @Id
    Long idSucursal;
    String nombreSucursal;
    String ciudadSucursal;
    Long franquiciaId;
}
