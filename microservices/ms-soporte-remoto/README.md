# ms-soporte-remoto

## Cobertura de rúbrica
- Persistencia real con JPA + Hibernate (entidades y relaciones OneToMany/ManyToOne)
- CRUD real con JpaRepository
- DTOs + Bean Validation
- ResponseEntity + @ControllerAdvice
- Logs estructurados con SLF4J
- OpenFeign con timeout y manejo de error
- Migraciones iniciales con Flyway

## Endpoints
- POST /api/soportes-remoto
- GET /api/soportes-remoto
- GET /api/soportes-remoto/{id}
- PUT /api/soportes-remoto/{id}
- DELETE /api/soportes-remoto/{id}