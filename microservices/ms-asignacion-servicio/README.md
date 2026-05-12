# ms-asignacion-servicio

## Cobertura de rúbrica
- Persistencia real con JPA + Hibernate (entidades y relaciones OneToMany/ManyToOne)
- CRUD real con JpaRepository
- DTOs + Bean Validation
- ResponseEntity + @ControllerAdvice
- Logs estructurados con SLF4J
- OpenFeign con timeout y manejo de error
- Migraciones iniciales con Flyway

## Endpoints
- POST /api/asignaciones-servicio
- GET /api/asignaciones-servicio
- GET /api/asignaciones-servicio/{id}
- PUT /api/asignaciones-servicio/{id}
- DELETE /api/asignaciones-servicio/{id}