# Clean Architecture - Estructura del Proyecto

Este proyecto sigue los principios de **Clean Architecture** para mantener una separación clara de responsabilidades y facilitar el mantenimiento y las pruebas.

## Estructura de Capas

```
com.franquicia.accenture
│
├── domain/                          # Capa de Dominio (Núcleo del negocio)
│   ├── model/                       # Entidades del dominio
│   │   ├── Franquicia.java
│   │   ├── Producto.java
│   │   └── Sucursal.java
│   ├── port/                        # Interfaces (Puertos)
│   │   └── output/                  # Puertos de salida (repositorios)
│   │       ├── FranquiciaRepositoryPort.java
│   │       ├── ProductoRepositoryPort.java
│   │       └── SucursalRepositoryPort.java
│   └── usecase/                     # Casos de uso (interfaces)
│       ├── FranquiciaUseCase.java
│       ├── ProductoUseCase.java
│       ├── SucursalUseCase.java
│       └── ProductoMaxStockUseCase.java
│
├── application/                     # Capa de Aplicación (Lógica de negocio)
│   ├── dto/                         # Data Transfer Objects
│   │   ├── CrearFranquiciaRequest.java
│   │   ├── CrearProductoRequest.java
│   │   ├── CrearSucursalRequest.java
│   │   ├── ActualizarStockRequest.java
│   │   ├── ProductoMaxStockResponse.java
│   │   ├── SucursalRequest.java
│   │   └── ProductoRequest.java
│   └── service/                     # Servicios de aplicación (implementan casos de uso)
│       ├── FranquiciaService.java
│       ├── ProductoService.java
│       └── SucursalService.java
│
├── infrastructure/                  # Capa de Infraestructura (Detalles técnicos)
│   └── persistence/                 # Persistencia de datos
│       ├── entity/                  # Entidades de persistencia (JPA/R2DBC)
│       │   ├── FranquiciaEntity.java
│       │   ├── ProductoEntity.java
│       │   └── SucursalEntity.java
│       ├── mapper/                   # Mappers entre dominio y persistencia
│       │   ├── FranquiciaEntityMapper.java
│       │   ├── ProductoEntityMapper.java
│       │   └── SucursalEntityMapper.java
│       └── repository/               # Implementaciones de repositorios
│           ├── FranquiciaRepositoryAdapter.java
│           ├── ProductoRepositoryAdapter.java
│           ├── SucursalRepositoryAdapter.java
│           ├── FranquiciaR2dbcRepository.java
│           ├── ProductoR2dbcRepository.java
│           └── SucursalR2dbcRepository.java
│
└── presentation/                    # Capa de Presentación (Interfaz con el usuario)
    └── controller/                  # Controladores REST
        ├── FranquiciaController.java
        ├── ProductoController.java
        └── SucursalController.java
```

## Principios de Clean Architecture

### 1. **Domain Layer (Dominio)**
- **Responsabilidad**: Contiene la lógica de negocio pura y las entidades del dominio.
- **Independencia**: No depende de ninguna otra capa.
- **Contenido**:
  - Entidades del dominio (`model/`)
  - Interfaces de repositorios (`port/output/`)
  - Interfaces de casos de uso (`usecase/`)

### 2. **Application Layer (Aplicación)**
- **Responsabilidad**: Orquesta los casos de uso y coordina el flujo de la aplicación.
- **Dependencias**: Solo depende de la capa Domain.
- **Contenido**:
  - DTOs para transferencia de datos
  - Servicios que implementan la lógica de aplicación
  - Mappers entre DTOs y entidades del dominio

### 3. **Infrastructure Layer (Infraestructura)**
- **Responsabilidad**: Implementa los detalles técnicos (persistencia, APIs externas, etc.).
- **Dependencias**: Depende de Domain y Application.
- **Contenido**:
  - Implementaciones de repositorios (adaptadores)
  - Entidades de persistencia
  - Mappers entre entidades de dominio y persistencia
  - Configuraciones técnicas

### 4. **Presentation Layer (Presentación)**
- **Responsabilidad**: Maneja la interacción con el usuario/cliente (REST APIs, GraphQL, etc.).
- **Dependencias**: Depende de Application y Domain.
- **Contenido**:
  - Controladores REST
  - DTOs de request/response
  - Validaciones de entrada

## Flujo de Datos

```
Request → Presentation (Controller) 
         → Application (Service) 
         → Domain (UseCase/Entity)
         → Infrastructure (Repository Adapter)
         → Database
```

## Ventajas de esta Arquitectura

1. **Testabilidad**: Cada capa puede ser testeada independientemente.
2. **Mantenibilidad**: Cambios en una capa no afectan directamente a otras.
3. **Escalabilidad**: Fácil agregar nuevas funcionalidades sin afectar el código existente.
4. **Independencia de frameworks**: El dominio no depende de Spring, JPA, etc.
5. **Reutilización**: La lógica de negocio puede ser reutilizada en diferentes contextos.

## Convenciones

- **Ports**: Interfaces que definen contratos (ej: `RepositoryPort`)
- **Adapters**: Implementaciones concretas de los ports (ej: `RepositoryAdapter`)
- **Entities**: Entidades del dominio (sin anotaciones de framework)
- **DTOs**: Objetos de transferencia de datos entre capas

## Ejemplo de Uso

```java
// Presentation Layer
@RestController
public class FranquiciaController {
    private final FranquiciaService service; // Application Layer
    // ...
}

// Application Layer
@Service
public class FranquiciaService {
    private final FranquiciaRepositoryPort repositoryPort; // Domain Port
    // ...
}

// Infrastructure Layer
@Repository
public class FranquiciaRepositoryAdapter implements FranquiciaRepositoryPort {
    private final FranquiciaR2dbcRepository r2dbcRepository; // Spring Data
    // ...
}
```
