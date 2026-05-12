CREATE TABLE IF NOT EXISTS soportes_remoto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(40) NOT NULL,
    referencia_externa_id BIGINT NULL,
    fecha_creacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS soportes_remoto_detalles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    soportes_remoto_id BIGINT NOT NULL,
    CONSTRAINT fk_soportes_remoto_detalles_soportes_remoto FOREIGN KEY (soportes_remoto_id) REFERENCES soportes_remoto(id) ON DELETE CASCADE
);