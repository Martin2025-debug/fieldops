CREATE TABLE IF NOT EXISTS asignaciones_servicio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(40) NOT NULL,
    referencia_externa_id BIGINT NULL,
    fecha_creacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS asignaciones_servicio_detalles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    asignaciones_servicio_id BIGINT NOT NULL,
    CONSTRAINT fk_asignaciones_servicio_detalles_asignaciones_servicio FOREIGN KEY (asignaciones_servicio_id) REFERENCES asignaciones_servicio(id) ON DELETE CASCADE
);