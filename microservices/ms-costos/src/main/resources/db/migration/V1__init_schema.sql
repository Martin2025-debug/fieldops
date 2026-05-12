CREATE TABLE IF NOT EXISTS costos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(40) NOT NULL,
    referencia_externa_id BIGINT NULL,
    fecha_creacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS costos_detalles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    costos_id BIGINT NOT NULL,
    CONSTRAINT fk_costos_detalles_costos FOREIGN KEY (costos_id) REFERENCES costos(id) ON DELETE CASCADE
);