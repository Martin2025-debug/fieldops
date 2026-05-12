# ms-costos

## Cobertura de rúbrica
- Persistencia real con JPA + Hibernate (entidades y relaciones OneToMany/ManyToOne)
- CRUD real con JpaRepository
- DTOs + Bean Validation
- ResponseEntity + @ControllerAdvice
- Logs estructurados con SLF4J
- OpenFeign con timeout y manejo de error
- Migraciones iniciales con Flyway

## Endpoints
- POST /api/costos
- GET /api/costos
- GET /api/costos/{id}
- PUT /api/costos/{id}
- DELETE /api/costos/{id}