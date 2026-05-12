CREATE TABLE IF NOT EXISTS evidencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(40) NOT NULL,
    referencia_externa_id BIGINT NULL,
    fecha_creacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS evidencias_detalles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    evidencias_id BIGINT NOT NULL,
    CONSTRAINT fk_evidencias_detalles_evidencias FOREIGN KEY (evidencias_id) REFERENCES evidencias(id) ON DELETE CASCADE
);