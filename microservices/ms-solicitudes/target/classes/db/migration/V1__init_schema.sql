CREATE TABLE IF NOT EXISTS solicitudes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(40) NOT NULL,
    referencia_externa_id BIGINT NULL,
    fecha_creacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS solicitudes_detalles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    solicitudes_id BIGINT NOT NULL,
    CONSTRAINT fk_solicitudes_detalles_solicitudes FOREIGN KEY (solicitudes_id) REFERENCES solicitudes(id) ON DELETE CASCADE
);