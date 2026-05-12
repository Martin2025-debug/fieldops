CREATE TABLE IF NOT EXISTS programaciones_visita (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(40) NOT NULL,
    referencia_externa_id BIGINT NULL,
    fecha_creacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS programaciones_visita_detalles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    programaciones_visita_id BIGINT NOT NULL,
    CONSTRAINT fk_programaciones_visita_detalles_programaciones_visita FOREIGN KEY (programaciones_visita_id) REFERENCES programaciones_visita(id) ON DELETE CASCADE
);