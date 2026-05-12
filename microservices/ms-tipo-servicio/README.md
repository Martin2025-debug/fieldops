# ms-tipo-servicio

## Cobertura de rúbrica
- Persistencia real con JPA + Hibernate (entidades y relaciones OneToMany/ManyToOne)
- CRUD real con JpaRepository
- DTOs + Bean Validation
- ResponseEntity + @ControllerAdvice
- Logs estructurados con SLF4J
- OpenFeign con timeout y manejo de error
- Migraciones iniciales con Flyway

## Endpoints
- POST /api/tipos-servicio
- GET /api/tipos-servicio
- GET /api/tipos-servicio/{id}
- PUT /api/tipos-servicio/{id}
- DELETE /api/tipos-servicio/{id}