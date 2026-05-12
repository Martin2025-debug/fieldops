CREATE TABLE IF NOT EXISTS tecnicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(40) NOT NULL,
    referencia_externa_id BIGINT NULL,
    fecha_creacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS tecnicos_detalles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    tecnicos_id BIGINT NOT NULL,
    CONSTRAINT fk_tecnicos_detalles_tecnicos FOREIGN KEY (tecnicos_id) REFERENCES tecnicos(id) ON DELETE CASCADE
);