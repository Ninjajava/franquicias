# API Franquicias - Spring Boot WebFlux

Microservicio para gestión de franquicias, sucursales y productos.

## Tecnologías
- Java 17
- Spring Boot 4
- WebFlux
- R2DBC
- MySQL
- Docker

## Endpoints principales

### Crear franquicia
POST /crear

### Crear sucursal
POST /franquicia/{id}/sucursal

### Crear producto
POST /sucursal/{id}/producto

### Eliminar producto
DELETE /producto/{id}

### Actualizar stock
PUT /producto/{id}/stock

### Producto con mayor stock de la franquicia
GET /franquicia/{id}/producto-max-stock

## Ejecución local

```bash
docker compose up -d
mvn spring-boot:run
