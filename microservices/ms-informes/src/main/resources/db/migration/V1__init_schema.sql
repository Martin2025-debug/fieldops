CREATE TABLE IF NOT EXISTS informes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(40) NOT NULL,
    referencia_externa_id BIGINT NULL,
    fecha_creacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS informes_detalles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    informes_id BIGINT NOT NULL,
    CONSTRAINT fk_informes_detalles_informes FOREIGN KEY (informes_id) REFERENCES informes(id) ON DELETE CASCADE
);